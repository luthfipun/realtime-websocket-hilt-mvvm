package github.luthfipun.cryptotracker.domain.util

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertTime(time: Long): String{
    val date = Date(time)
    val sdf = SimpleDateFormat("HH:mm:ss")
    return sdf.format(date)
}

fun convertPrice(price: String?): String {
    if (price.isNullOrEmpty()){
        return "-"
    }
    return DecimalFormat("#.##").format(price.toDouble())
}