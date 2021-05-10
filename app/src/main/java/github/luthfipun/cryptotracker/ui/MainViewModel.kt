package github.luthfipun.cryptotracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.luthfipun.cryptotracker.domain.model.Trade
import github.luthfipun.cryptotracker.domain.util.DataState
import github.luthfipun.cryptotracker.repository.MainRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _dataState = MutableLiveData<DataState<Trade>>()
    val dataState: LiveData<DataState<Trade>>
        get() = _dataState

    init {
        setStateEvent(MainStateEvent.GetDataEvent)
    }

    private fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent){
                MainStateEvent.GetDataEvent -> {
                    mainRepository.observeData()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MainStateEvent{
    object GetDataEvent: MainStateEvent()
}