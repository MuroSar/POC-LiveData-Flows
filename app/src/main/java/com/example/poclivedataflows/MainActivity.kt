package com.example.poclivedataflows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.poclivedataflows.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * You can see here this implementation activates the viewModel functions on click
         */
        //Live Data
        binding.btnLiveData.setOnClickListener {
            viewModel.triggerLiveData()
        }
        //State Flow
        binding.btnStateFlow.setOnClickListener {
            viewModel.triggerStateFlow()
        }
        //Flow
        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }
        }
        //Shared Flow
        binding.btnSharedFlow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }
        subscribeToObservable()
    }
    /**
     * When the Activity rotates or when it changes to dark mode the observers will be notified automatically.
     */
    private fun subscribeToObservable() {
        /**
         * The changes generated in the ViewModel will activate the observer and we change the text.
         */
        viewModel.liveData.observe(this) {
            binding.tvLiveData.text = it
        }
        /**
         * Uncomment the SnackBar to be able to see the difference between state flow and shared flow.
         * Don't forget to comment on the text assignment.
         */
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                binding.tvStateFlow.text = it
                /*
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
                */
            }
        }
        /**
         * The changes generated in the ViewModel will activate the observer and we can see the SnackBar
         */
        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest {
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    }
}
