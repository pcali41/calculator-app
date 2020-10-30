package com.example.calculator.calculator

import android.os.Bundle
import android.view.*
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.calculator.R
import com.example.calculator.database.CalculationDatabase
import com.example.calculator.databinding.FragmentCalculatorBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A [Fragment] representing the app's main arithmetic calculator interface.
 */
class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Setup the app bar and options menu
        setupAppBar()

        // Initialize the binding object
        val binding: FragmentCalculatorBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calculator, container, false)

        // Allow data binding to observe LiveData with this fragment's lifecycle
        binding.lifecycleOwner = this

        // Give the binding access to a new viewModel
        val viewModel = getViewModel()
        binding.viewModel = viewModel

        // Setup the DELETE button's onLongClick listener
        setupOnLongClickListeners(binding, viewModel)

        // Setup the observer functions for the viewModel's LiveData
        setupViewModelObservers(binding, viewModel)

        // Inflate the layout for this fragment
        return binding.root
    }

    /**
     * Gives the binding object access to a newly constructed [CalculatorViewModel].
     */
    private fun getViewModel(): CalculatorViewModel {
        val application = requireNotNull(this.activity).application
        val dataSource = CalculationDatabase.getInstance(application).calculationDatabaseDAO

        val expression = CalculatorFragmentArgs.fromBundle(requireArguments()).initialExpression
        val calculator: StringCalculator = StringSplitCalculator(expression)

        val viewModelFactory = CalculatorViewModelFactory(dataSource, calculator)
        val viewModel: CalculatorViewModel by viewModels { viewModelFactory }

        return viewModel
    }

    /**
     * Sets up all long-click listener methods.
     *
     * This should only be called once all view binding objects have
     * been initialized and assigned to their corresponding variables.
     */
    private fun setupOnLongClickListeners(
        binding: FragmentCalculatorBinding,
        viewModel: CalculatorViewModel
    ) {
        // Handle long-clicks with the DELETE button as clear actions
        binding.deleteBtn.setOnLongClickListener {
            viewModel.onClear()
            true
        }
    }

    /**
     * Sets up the app bar and enables its corresponding options menu
     */
    private fun setupAppBar() {
        // Set the app bar's title
        val appBar = (activity as AppCompatActivity).supportActionBar
        appBar?.title = getString(R.string.title_calculator)

        // Enable the app bar's option menu
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_calculator_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_history -> {
                findNavController().navigate(CalculatorFragmentDirections
                    .actionCalculatorFragmentToHistoryFragment())

                item.isVisible = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sets up all observer functions for the [CalculatorViewModel]
     */
    private fun setupViewModelObservers(
        binding: FragmentCalculatorBinding,
        viewModel: CalculatorViewModel
    ) {
        // Auto-scroll the output window to the end when the expression is modified
        viewModel.expression.observe(viewLifecycleOwner) { expression ->
            viewLifecycleOwner.lifecycleScope.launch {
                expression?.let {
                    // Wait long enough for the TextView to resize before scrolling
                    delay(50)

                    binding.outputScroll.scrollToRight()
                }
            }
        }

        // Auto-scroll the result window to the end when the result is modified
        viewModel.resultPreview.observe(viewLifecycleOwner) { resultPreview ->
            viewLifecycleOwner.lifecycleScope.launch {
                resultPreview?.let {
                    // Wait long enough for the TextView to resize before scrolling
                    delay(50)

                    binding.resultScroll.scrollToRight()
                }
            }
        }
    }

    /**
     * Scrolls a [HorizontalScrollView] to the right-most point of the view.
     */
    private fun HorizontalScrollView.scrollToRight() {
        val lastChild = getChildAt(childCount - 1)
        val right = lastChild.right + paddingRight
        val delta = right - (scrollX + width)
        smoothScrollBy(delta, 0)
    }
}