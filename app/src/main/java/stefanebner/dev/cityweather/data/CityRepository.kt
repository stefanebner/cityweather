package stefanebner.dev.cityweather.data

import android.arch.lifecycle.Observer
import org.jetbrains.anko.doAsync
import stefanebner.dev.cityweather.BuildConfig
import stefanebner.dev.cityweather.data.database.CityDao
import stefanebner.dev.cityweather.data.network.NetworkUtils
import stefanebner.dev.cityweather.data.network.OpenWeatherDataSource
import stefanebner.dev.cityweather.model.City
import stefanebner.dev.cityweather.model.Sys
import stefanebner.dev.cityweather.model.WeatherEntry
import stefanebner.dev.cityweather.utils.JsonParser
import java.io.InputStream

class CityRepository private constructor(
       private val dao: CityDao,
       private val dataSourceOpen: OpenWeatherDataSource
) {

    init {
        val networkData = dataSourceOpen.getAllCities()
        val observer = Observer<List<City>> { cities -> doAsync { dao.insertAll(cities) } }
        networkData.observeForever(observer)
    }

    companion object {
        @Volatile private var INSTANCE: CityRepository? = null

        fun getInstance(dao: CityDao, dataSourceOpen: OpenWeatherDataSource) : CityRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: createWeatherRepository(dao, dataSourceOpen).also { INSTANCE = it}
                }

        private fun createWeatherRepository(dao: CityDao, dataSourceOpen: OpenWeatherDataSource) =
                CityRepository(dao, dataSourceOpen)
    }

    // id	nm	lat	lon	countryCode
    fun startLocalCitySync(inputStream: InputStream) {
        doAsync {
            // roughly 74k entries in the text file, restart import if not all are parsed
            if (getNumberOfCitiesInDatabase() < 74000) {
                inputStream.bufferedReader().forEachLine { dao.insertCity(City(it.split("\t"))) }
            }
        }
    }

    fun getAll() = dao.getAll()

    fun getCityById(id: Int) = dao.getWeatherForId(id)

    private fun getNumberOfCitiesInDatabase() = dao.getNumberOfEntries()

    private fun updateCity(city: City) = dao.insertCity(city)

    fun updateWeatherForCity(name: String) {
        dataSourceOpen.requestData(NetworkUtils().getCityByUrl(name, "", BuildConfig.OpenWeatherApiKey))
    }

    fun updateWeatherForId(id: Int) {
        dataSourceOpen.requestData(NetworkUtils().getCityById(id, "", BuildConfig.OpenWeatherApiKey))
    }

    fun searchForCity(searchTerm: String) = dao.findCityByName(searchTerm)
}