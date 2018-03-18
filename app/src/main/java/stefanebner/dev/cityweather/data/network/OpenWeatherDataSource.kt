package stefanebner.dev.cityweather.data.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import org.jetbrains.anko.doAsync
import stefanebner.dev.cityweather.data.database.CityDao
import stefanebner.dev.cityweather.model.City
import stefanebner.dev.cityweather.model.WeatherEntry
import stefanebner.dev.cityweather.utils.JsonParser

class OpenWeatherDataSource private constructor(
        val context: Context,
        val dao: CityDao
) {

    private val cities: MutableLiveData<List<City>> = MutableLiveData()

    companion object {
        @Volatile private var INSTANCE: OpenWeatherDataSource? = null

        fun getInstance(context: Context, dao: CityDao) : OpenWeatherDataSource =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: createDataSource(context, dao).also { INSTANCE = it}
                }

        private fun createDataSource(context: Context, dao: CityDao) : OpenWeatherDataSource =
                OpenWeatherDataSource(context, dao)
    }

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