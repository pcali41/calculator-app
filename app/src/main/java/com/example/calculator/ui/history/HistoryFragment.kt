package com.example.calculator.ui.history

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calculator.R
import com.example.calculator.databinding.FragmentHistoryBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] representing a list of saved math calculations.
 */
@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Setup the app bar and options menu
        setupAppBar()

        // Initialize the binding object
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history, container, false)

        // Allow data binding to observe LiveData with the lifecycle of this fragment
        binding.lifecycleOwner = this

        // Give the binding access to a new viewModel
        binding.viewModel = viewModel

        // Give the binding access to a new RecyclerView adapter
        val adapter = getHistoryAdapter(viewModel)
        binding.historyList.adapter = adapter

        // Setup the observer functions for the viewModel's LiveData
        setupViewModelObservers(viewModel, adapter)

        // Inflate the layout for this fragment
        return binding.root
    }

    /**
     * Sets up the app bar and its corresponding options menu.
     */
    private fun setupAppBar() {
        // Set the app bar's title
        val appBar = (activity as AppCompatActivity).supportActionBar
        appBar?.title = getString(R.string.title_history)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_history -> {
                binding.viewModel?.onClearHistory()

                findNavController().navigateUp()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Returns a newly constructed [HistoryAdapter] instance.
     */
    private fun getHistoryAdapter(viewModel: HistoryViewModel): HistoryAdapter {
        return HistoryAdapter(CalculationListener { id ->
            viewModel.onLoadCalculation(id)
        })
    }

    /**
     * Sets up all necessary observer functions for the [HistoryViewModel].
     */
    private fun setupViewModelObservers(
        viewModel: HistoryViewModel,
        adapter: HistoryAdapter,
    ) {
        // Submit a new list of recorded calculations when updated
        viewModel.calculations.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        // Navigate to the calculator fragment using the selected expression
        viewModel.loadSelectedCalculation.observe(viewLifecycleOwner, { expression ->
            expression?.let {
                findNavController().navigate(HistoryFragmentDirections
                    .actionHistoryFragmentToCalculatorFragment(expression))

                viewModel.doneLoadingCalculation()
            }
        })

        // Display a Snackbar to notify the user that the history was cleared
        viewModel.showSnackbarEvent.observe(viewLifecycleOwner, {
            if (it) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    R.string.cleared_history_message,
                    Snackbar.LENGTH_SHORT).show()

                viewModel.doneShowingSnackbar()
            }
        })
    }
}