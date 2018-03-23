package stefanebner.dev.cityweather.data.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import org.jetbrains.anko.doAsync
import stefanebner.dev.cityweather.data.database.CityDao
import stefanebner.dev.cityweather.data.database.CityDatabase
import stefanebner.dev.cityweather.model.City
import stefanebner.dev.cityweather.model.WeatherEntry
import stefanebner.dev.cityweather.utils.JsonParser

class OpenWeatherDataSource(
        val context: Context,
        database: CityDatabase
) {

    private val dao: CityDao = database.cityDatabase.cityDao()
    private val cities: MutableLiveData<List<City>> = MutableLiveData()

    fun getAllCities(): LiveData<List<City>> = cities

    fun requestData(url: String) {
        doAsync {
            val json = NetworkUtils().getJsonResponse(url)
            val entry: WeatherEntry? = json?.let { JsonParser().parseWeatherFromJson(it) }
            entry?.run { dao.insertCity(City(entry)) }
        }
    }

    fun syncWeatherEntries() {
        //TODO OPTIONAL: make plan how to slowly sync all data, starting with searched cities first
    }
}