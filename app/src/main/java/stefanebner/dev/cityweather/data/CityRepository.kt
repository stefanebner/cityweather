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
                inputStream.bufferedReader().forEachLine {
                    val line = it.split("\t")
                    dao.insertCity(City(line[0].toInt(), line[1], line[4], line[2].toDouble(),
                            line[3].toDouble(), 0.0, 0, false, 0.0, 0.0, "", 0, 0, 0.0))
                }
            }
        }
    }

    fun getLatestTen(timestamp: Long) = dao.getLatestTenSearched(timestamp)

    fun getAll() = dao.getAll()

    fun getCityById(id: Int) = dao.getWeatherForId(id)

    private fun getNumberOfCitiesInDatabase() = dao.getNumberOfEntries()

    private fun updateCity(city: City) = dao.insertCity(city)

    fun updateWeatherForCity(name: String) {
        requestData(NetworkUtils().getCityByUrl(name, "", BuildConfig.OpenWeatherApiKey))
    }

    fun updateWeatherForId(id: Int) {
        requestData(NetworkUtils().getCityById(id, "", BuildConfig.OpenWeatherApiKey))
    }

    private fun requestData(url: String) {
        doAsync {
            val json = NetworkUtils().getJsonResponse(url)
            val entry: WeatherEntry? = json?.let {
                JsonParser().parseWeatherFromJson(it)
            }
            entry?.apply {
                val city: City = with(entry) {
                    var mainDescription = ""
                    weather?.let { mainDescription = it[0].description }
                    City(id, name, sys.country, coord.lon, coord.lat, main.temp, dt, true,
                            main.tempMin, main.tempMax, mainDescription, main.pressure,
                            main.humidity, wind.speed)
                }
                updateCity(city)
            }
        }
    }

    fun searchForCity(searchTerm: String) = dao.findCityByName(searchTerm)
}