package github.luthfipun.cryptotracker.repository

import github.luthfipun.cryptotracker.domain.model.Trade
import github.luthfipun.cryptotracker.domain.util.DataState
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun observeData(): Flow<DataState<Trade>>
}