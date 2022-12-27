package it.marcodallaba.beerbox.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.data.source.local.BeersDatabase
import it.marcodallaba.beerbox.data.source.remote.PunkService
import it.marcodallaba.beerbox.util.BeerType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeersRepository @Inject constructor(
    private val db: BeersDatabase,
    private val service: PunkService,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getBeersPager(pageSize: Int, query: String, beerType: BeerType?): Flow<PagingData<Beer>> {

        val dbQuery = "%${query.replace(' ', '%')}%"

        return Pager(
            config = PagingConfig(pageSize), remoteMediator = BeersRemoteMediator(db, service)
        ) {
            db.beersDao().getBeersPagingSource(dbQuery, beerType?.minEbc, beerType?.maxEbc)
        }.flow
    }

}