package stefanebner.dev.cityweather.utils

import android.support.v7.widget.RecyclerView
import android.view.View
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

fun RecyclerView.showView(visible: Boolean) {
    visibility = when (visible) {
        true -> View.VISIBLE
        false -> View.INVISIBLE
    }
}