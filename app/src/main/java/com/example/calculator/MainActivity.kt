package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CalculatorViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val calculator: StringCalculator = StringCalculatorImpl()
        val viewModelFactory = CalculatorViewModelFactory(calculator)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CalculatorViewModel::class.java)

        // Allows data binding to observe LiveData with the lifecycle of this activity
        binding.lifecycleOwner = this

        // Gives the binding access to the viewModel
        binding.viewModel = viewModel

        binding.deleteBtn.setOnLongClickListener {
            viewModel.onClear()
            true
        }
    }
}
