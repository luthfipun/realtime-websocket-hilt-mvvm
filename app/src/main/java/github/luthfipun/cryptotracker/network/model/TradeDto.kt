package github.luthfipun.cryptotracker.network.model

import com.google.gson.annotations.SerializedName
import github.luthfipun.cryptotracker.domain.model.Trade

data class TradeDto(
    @SerializedName("e")
    var type: String,
    @SerializedName("p")
    var price: String,
    @SerializedName("t")
    var time: Long
) {
    fun toTrade(): Trade {
        return Trade(type, price, time)
    }
}