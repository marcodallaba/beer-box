package it.marcodallaba.beerbox.ui.beer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import it.marcodallaba.beerbox.R
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.databinding.BeerBottomSheetDialogBinding

class BeerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val BEER_KEY = "BEER"

        @JvmStatic
        fun newInstance(beer: Beer) = BeerBottomSheetDialogFragment().apply {
            arguments = bundleOf(BEER_KEY to beer)
        }
    }

    private lateinit var binding: BeerBottomSheetDialogBinding

    override fun getTheme(): Int = R.style.BeerBottomSheetDialogFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BeerBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val beer: Beer? = arguments?.getSerializable(BEER_KEY) as Beer?
        Picasso.get().load(beer?.imageUrl).into(binding.imageView)
        binding.titleTextView.text = beer?.name
        binding.tagLine.text = beer?.tagLine
        binding.description.text = beer?.description
    }
}