package it.marcodallaba.beerbox.ui.beers

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.databinding.BeerItemBinding

class BeerViewHolder(
    val binding: BeerItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    var beer: Beer? = null
        set(value) {
            field = value
            Picasso.get().load(field?.imageUrl).into(binding.imageView)
            binding.titleTextView.text = field?.name
            binding.tagLine.text = field?.tagLine
            binding.description.text = field?.description
        }
}
