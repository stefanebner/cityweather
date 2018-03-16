package stefanebner.dev.cityweather.ui.cityDetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.city_extra_info.*
import kotlinx.android.synthetic.main.city_main_info.*
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.model.City
import stefanebner.dev.cityweather.utils.InjectorUtils
import stefanebner.dev.cityweather.utils.roundToTwoDecimals
import java.util.*

class CityDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CityDetailViewModel
    val cityId = "CITY_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra(cityId, -1)

        val factory = InjectorUtils().provideDetailViewModelFactory(this, id)
        viewModel = ViewModelProviders.of(this, factory)
                .get(CityDetailViewModel::class.java)

        viewModel.getCityInformation().observe(this, Observer {
            displayCity(it)
        })
    }

    private fun displayCity(city: City?) {
        city?.let {
            date.text = Date(it.date).toString()
            description.text = it.description
            currentTemperature.text = it.temp.roundToTwoDecimals().toString()
            hightestTemperature.text = it.tempMax.roundToTwoDecimals().toString()
            lowestTemperature.text = it.tempMin.roundToTwoDecimals().toString()
//            pressure.text = it.pressure.toString()
        }
    }
}