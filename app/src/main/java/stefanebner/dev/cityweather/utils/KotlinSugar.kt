package stefanebner.dev.cityweather.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlin.math.pow
import kotlin.math.round

/*
 * Various kotlin syntactic sugar methods
 */
fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}

fun Double.roundToTwoDecimals(): Double {
    val power = (10.0).pow(2)
    return round(this * power) / power
}