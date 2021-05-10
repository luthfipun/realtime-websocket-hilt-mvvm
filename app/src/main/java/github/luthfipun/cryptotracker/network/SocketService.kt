package github.luthfipun.cryptotracker.network

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import github.luthfipun.cryptotracker.network.model.TradeDto

interface SocketService {
    @Receive
    suspend fun observeWebSocketEvent(): WebSocket.Event

    @Send
    suspend fun sendSubscribe()

    @Receive
    suspend fun observeData(): TradeDto
}