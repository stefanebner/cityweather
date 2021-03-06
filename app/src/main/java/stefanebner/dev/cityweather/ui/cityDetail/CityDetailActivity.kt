package stefanebner.dev.cityweather.ui.cityDetail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.city_extra_info.*
import kotlinx.android.synthetic.main.city_main_info.*
import org.koin.android.architecture.ext.viewModel
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.model.City
import stefanebner.dev.cityweather.utils.loadImage
import stefanebner.dev.cityweather.utils.roundToTwoDecimals
import java.text.SimpleDateFormat
import java.util.*

class CityDetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<CityDetailViewModel>()
    val cityId = "CITY_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel.getCityInformation(intent.getIntExtra(cityId, -1)).observe(this, Observer {
            displayCity(it)
        })
    }

    private fun displayCity(city: City?) {
        city?.let {
            val sdf = SimpleDateFormat("EEE, d MMM - HH:mm:ss", resources.configuration.locales[0])
            main_date.text = sdf.format(Date(it.date * 1000L))
            main_city_name.text = city.name
            main_description.text = it.description
            main_current_temperature.text = it.temp.roundToTwoDecimals().toString() + 0x00B0.toChar()
            main_highest_temperature.text = it.tempMax.roundToTwoDecimals().toString() + 0x00B0.toChar()
            main_lowest_temperature.text = it.tempMin.roundToTwoDecimals().toString() + 0x00B0.toChar()
            extra_pressure.text = it.pressure.toString()
            extra_humidity.text = it.humidity.toString()
            extra_wind.text = it.wind.toString() + "m/s"
            extra_cloudiness.text = it.cloudiness.toString()
            main_weather_icon.loadImage("http://openweathermap.org/img/w/" + it.icon + ".png")
            setBackground(it.weatherCode)
        }
    }

    private fun setBackground(id: Int) {
        when (id) {
            in 200..299 -> main_background.setImageResource(R.color.colorThunder)
            in 300..399 -> main_background.setImageResource(R.color.colorDrizzle)
            in 500..599 -> main_background.setImageResource(R.color.colorRain)
            in 600..699 -> main_background.setImageResource(R.color.colorSnow)
            in 700..799 -> main_background.setImageResource(R.color.colorAtmosphere)
            800 -> main_background.setImageResource(R.color.colorSun)
            else -> main_background.setImageResource(R.color.colorBasic)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}