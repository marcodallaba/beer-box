package it.marcodallaba.beerbox.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.data.source.remote.PunkPagingSource
import it.marcodallaba.beerbox.data.source.remote.PunkService
import it.marcodallaba.beerbox.util.BeerType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeersRepository @Inject constructor(private val service: PunkService) {

    fun getBeersStream(query: String?, beerType: BeerType?): Flow<PagingData<Beer>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { PunkPagingSource(service, query, beerType) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}