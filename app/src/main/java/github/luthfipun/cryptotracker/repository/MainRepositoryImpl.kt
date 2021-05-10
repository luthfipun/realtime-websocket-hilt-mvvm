package github.luthfipun.cryptotracker.repository

import github.luthfipun.cryptotracker.domain.model.Trade
import github.luthfipun.cryptotracker.domain.util.DataState
import github.luthfipun.cryptotracker.network.SocketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val socketService: SocketService
) : MainRepository {

    override suspend fun observeData(): Flow<DataState<Trade>> = flow {
        emit(DataState.Loading)

        try {
            val trades = socketService.observeTrade()
            trades.collect {
                emit(DataState.Success(it.toTrade()))
            }
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}