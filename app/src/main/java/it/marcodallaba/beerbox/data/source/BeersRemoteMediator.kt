package it.marcodallaba.beerbox.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.data.source.local.BeersDao
import it.marcodallaba.beerbox.data.source.local.BeersDatabase
import it.marcodallaba.beerbox.data.source.remote.PunkService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalPagingApi::class)
class BeersRemoteMediator(
    private val database: BeersDatabase, private val punkService: PunkService
) : RemoteMediator<Int, Beer>() {

    private val beersDao: BeersDao = database.beersDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Beer>): MediatorResult {
        return try {
            // The network load method takes an optional after=<beer.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey: Int = when (loadType) {
                LoadType.REFRESH -> 1
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    lastItem.id / state.config.pageSize + 1
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = punkService.getBeers(
                beerName = null,
                minEgt = null,
                maxEgt = null,
                page = loadKey,
                perPage = state.config.pageSize
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    beersDao.clearAllBeers()
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                val insertTime = System.currentTimeMillis()
                response.forEach { it.insertTime = insertTime }
                beersDao.insertBeers(response)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val oldestInsertTime = beersDao.getOldestInsertTime()
        return if (System.currentTimeMillis() - oldestInsertTime <= cacheTimeout) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

}