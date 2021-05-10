package github.luthfipun.cryptotracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import github.luthfipun.cryptotracker.databinding.ActivityMainBinding
import github.luthfipun.cryptotracker.domain.model.Trade
import github.luthfipun.cryptotracker.domain.util.DataState
import github.luthfipun.cryptotracker.domain.util.convertPrice
import github.luthfipun.cryptotracker.domain.util.convertTime

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
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    // No loading for this time
                }
                is DataState.Success -> {
                    displayData(dataState.data)
                }
            }
        })
    }

    private fun displayData(trade: Trade){
        if (trade.price.contains("-")){
            binding.txtSell.text = convertPrice(trade.price)
        }else{
            binding.txtBuy.text = convertPrice(trade.price)
        }
        binding.txtTime.text = convertTime(trade.time)
    }

    private fun displayError(message: String?){
        Snackbar.make(binding.container, message.orEmpty(), Snackbar.LENGTH_LONG).show()
    }
}