package github.luthfipun.cryptotracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import github.luthfipun.cryptotracker.databinding.ActivityMainBinding
import github.luthfipun.cryptotracker.domain.util.DataState

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, { dataState ->
            when(dataState){
                is DataState.Error -> {
                    Log.e("ENOG", "ERROR => ${dataState.exception.message}")
                }
                is DataState.Loading -> {}
                is DataState.Success -> {
                    Log.e("ENOG", "DATA => ${dataState.data.time}")
                }
            }
        })
    }
}