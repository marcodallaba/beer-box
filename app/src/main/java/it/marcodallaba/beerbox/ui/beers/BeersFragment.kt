package it.marcodallaba.beerbox.ui.beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import it.marcodallaba.beerbox.R
import it.marcodallaba.beerbox.data.Beer
import it.marcodallaba.beerbox.databinding.FragmentBeersBinding
import it.marcodallaba.beerbox.ui.beer.BeerBottomSheetDialogFragment
import it.marcodallaba.beerbox.util.BeerType
import it.marcodallaba.beerbox.util.BeerTypeId
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeersFragment : Fragment(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private lateinit var binding: FragmentBeersBinding

    private val beersViewModel: BeersViewModel by viewModels()

    private var beersAdapter: BeersAdapter = BeersAdapter()
    private val layoutManager = LinearLayoutManager(context)
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeersBinding.inflate(inflater, container, false)
        searchBeers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = layoutManager
        beersAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.recyclerView.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart <= layoutManager.findFirstVisibleItemPosition())
                    binding.recyclerView.scrollToPosition(0)
            }
        })
        binding.recyclerView.adapter = beersAdapter
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        context?.let { ContextCompat.getDrawable(it, R.drawable.list_divider_dark) }
            ?.let { dividerItemDecoration.setDrawable(it) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        binding.searchView.setOnQueryTextListener(this)

        val searchCloseButtonId = binding.searchView.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
        binding.searchView.findViewById<View>(searchCloseButtonId).setOnClickListener { onClose() }

        beersAdapter.onMoreInfo.observe(viewLifecycleOwner) {
            openBeerBottomSheet(it)
        }

        addFilters(beersViewModel.beerTypes)
    }

    private fun searchBeers() {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            beersViewModel.getBeers().collectLatest {
                beersAdapter.submitData(it)
            }
        }
    }

    override fun onClose(): Boolean {
        binding.searchView.setQuery("", true)
        binding.searchView.clearFocus()
        beersViewModel.currentQuery = null
        searchBeers()
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            beersViewModel.currentQuery = query
            searchBeers()
        }
        return true
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        if (newText != null) {
            beersViewModel.currentQuery = newText
            searchBeers()
            binding.searchView.clearFocus()
        }
        return true
    }

    private fun openBeerBottomSheet(beer: Beer) {
        BeerBottomSheetDialogFragment.newInstance(beer).apply {
            show(
                this@BeersFragment.parentFragmentManager,
                BottomSheetDialogFragment::class.java.canonicalName
            )
        }
    }

    private fun onChipChanged(view: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            beersViewModel.currentBeerType = (view.tag as BeerType)
            searchBeers()
        } else {
            beersViewModel.currentBeerType = null
            searchBeers()
        }
    }

    private fun addFilters(beerTypes: List<BeerType>?) {
        val layoutInflater = LayoutInflater.from(context)
        beerTypes?.forEach {
            val chip: Chip =
                layoutInflater.inflate(R.layout.filter_chip, binding.filterChipGroup, false) as Chip
            chip.id = BeerTypeId.values().getOrNull(it.ordinal)?.id ?: View.generateViewId()
            chip.text = it.displayName
            chip.tag = it
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                onChipChanged(
                    buttonView,
                    isChecked
                )
            }
            TransitionManager.beginDelayedTransition(binding.filterChipGroup)
            binding.filterChipGroup.addView(chip)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BeersFragment()
    }
}
