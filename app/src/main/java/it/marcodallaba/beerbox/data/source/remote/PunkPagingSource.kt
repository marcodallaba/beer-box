package it.marcodallaba.beerbox.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.util.BeerType


class PunkPagingSource(
    private val service: PunkService,
    private val query: String?,
    private val beerType: BeerType?
) : PagingSource<Int, Beer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val beersList =
                service.getBeers(query, beerType?.minEbc, beerType?.maxEbc, page, params.loadSize)
            val reachedLastPage = beersList.isEmpty()
            LoadResult.Page(
                data = beersList,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (reachedLastPage) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}