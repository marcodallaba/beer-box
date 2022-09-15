package it.marcodallaba.beerbox.ui.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.data.source.BeersRepository
import it.marcodallaba.beerbox.util.BeerType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(private val beersRepository: BeersRepository) :
    ViewModel() {

    val beerTypes = BeerType.values()
        .filter { it != BeerType.UNKNOWN }
        .sortedBy { it.displayName }

    fun getBeers(): Flow<PagingData<Beer>> {
        return beersRepository.getBeersStream().cachedIn(viewModelScope)
    }
}