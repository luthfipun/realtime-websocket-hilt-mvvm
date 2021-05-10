package github.luthfipun.cryptotracker.network

import com.tinder.scarlet.ws.Receive
import github.luthfipun.cryptotracker.network.model.TradeDto
import kotlinx.coroutines.flow.Flow

interface SocketService {
    @Receive
    fun observeTrade(): Flow<TradeDto>
}