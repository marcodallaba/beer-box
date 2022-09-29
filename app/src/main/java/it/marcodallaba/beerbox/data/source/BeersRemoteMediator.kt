package it.marcodallaba.beerbox.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.data.RemoteKeys
import it.marcodallaba.beerbox.data.source.local.BeersDao
import it.marcodallaba.beerbox.data.source.local.BeersDatabase
import it.marcodallaba.beerbox.data.source.local.RemoteKeysDao
import it.marcodallaba.beerbox.data.source.remote.PunkService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalPagingApi::class)
class BeersRemoteMediator(
    private val database: BeersDatabase, private val punkService: PunkService
) : RemoteMediator<Int, Beer>() {

    private val beersDao: BeersDao = database.beersDao()
    private val remoteKeysDao: RemoteKeysDao = database.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Beer>): MediatorResult {
        return try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextKey
                }
            }

            val response = punkService.getBeers(
                page = page, perPage = state.config.pageSize
            )
            val endOfPaginationReached = response.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    beersDao.clearAllBeers()
                    remoteKeysDao.clearRemoteKeys()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.map {
                    RemoteKeys(beerId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeysDao.insertAll(keys)
                // Insert new beers into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                val insertTime = System.currentTimeMillis()
                response.forEach { it.insertTime = insertTime }
                beersDao.insertBeers(response)
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val oldestInsertTime =
            beersDao.getOldestInsertTime() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Beer>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { beer ->
            // Get the remote keys of the last item retrieved
            remoteKeysDao.remoteKeysBeerId(beer.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Beer>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { beer ->
                // Get the remote keys of the first items retrieved
                remoteKeysDao.remoteKeysBeerId(beer.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Beer>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { beerId ->
                remoteKeysDao.remoteKeysBeerId(beerId)
            }
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}