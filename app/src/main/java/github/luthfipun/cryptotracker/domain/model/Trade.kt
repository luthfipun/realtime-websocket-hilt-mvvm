package github.luthfipun.cryptotracker.domain.model

data class Trade(
    val type: String,
    val price: String,
    val time: Long
)