package it.marcodallaba.beerbox.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import it.marcodallaba.beerbox.data.source.local.BeersDatabase
import it.marcodallaba.beerbox.data.source.remote.PunkService
import javax.inject.Inject

class BeersRepository @Inject constructor(
    private val db: BeersDatabase,
    private val service: PunkService,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getBeersPager(pageSize: Int) = Pager(
        config = PagingConfig(pageSize), remoteMediator = BeersRemoteMediator(db, service)
    ) {
        db.beersDao().getBeersPagingSource()
    }.flow

}