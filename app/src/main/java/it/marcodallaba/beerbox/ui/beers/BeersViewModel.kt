package it.marcodallaba.beerbox.ui.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.data.source.BeersRepository
import it.marcodallaba.beerbox.util.BeerType
import it.marcodallaba.beerbox.util.type
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(private val beersRepository: BeersRepository) :
    ViewModel() {

    val beerTypes = BeerType.values().filter { it != BeerType.UNKNOWN }.sortedBy { it.displayName }

    var currentQuery: String = ""

    var currentBeerType: BeerType? = null

    private val beersSource =
        beersRepository.getBeersPager(NETWORK_PAGE_SIZE).cachedIn(viewModelScope)

    val beers = beersSource.map { pagingData: PagingData<Beer> ->
        pagingData.filter { beer ->
            val queryFilter = beer.name.contains(currentQuery, true) || beer.tagLine?.contains(
                currentQuery, true
            ) == true || beer.description?.contains(currentQuery, true) == true

            val beerTypeFilter = currentBeerType == null || beer.type() == currentBeerType

            queryFilter && beerTypeFilter
        }
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}