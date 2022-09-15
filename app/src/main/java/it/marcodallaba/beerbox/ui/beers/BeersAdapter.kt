package it.marcodallaba.beerbox.ui.beers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.databinding.BeerItemBinding

class BeersAdapter : PagingDataAdapter<Beer, BeerViewHolder>(DiffCallback()) {

    private val _onMoreInfo: MutableLiveData<Beer> = MutableLiveData()
    val onMoreInfo: LiveData<Beer> = _onMoreInfo

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BeerItemBinding.inflate(layoutInflater, parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = getItem(position)
        holder.beer = beer
        holder.binding.moreInfo.setOnClickListener {
            beer?.let {
                _onMoreInfo.value = it
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }

    }

}
