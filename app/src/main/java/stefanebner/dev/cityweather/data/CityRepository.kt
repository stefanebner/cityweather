package stefanebner.dev.cityweather.data

import android.arch.lifecycle.Observer
import org.jetbrains.anko.doAsync
import stefanebner.dev.cityweather.data.database.CityDao
import stefanebner.dev.cityweather.data.database.CityDatabase
import stefanebner.dev.cityweather.data.network.OpenWeatherDataSource
import stefanebner.dev.cityweather.model.City
import java.io.InputStream

class CityRepository(
       database: CityDatabase,
       private val dataSourceOpen: OpenWeatherDataSource
) {

    private val dao: CityDao

    init {
        val networkData = dataSourceOpen.getAllCities()
        dao = database.cityDatabase.cityDao()
        val observer = Observer<List<City>> { cities -> doAsync { dao.insertAll(cities) } }
        networkData.observeForever(observer)
    }

    // id nm lat lon countryCode ->
    // city ID, Name, latitude, longitude, 2letter country code
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

    fun updateWeatherForCity(name: String) = dataSourceOpen.updateCityData(name)

    fun updateWeatherForId(id: Int) = dataSourceOpen.updateCityData(id)

    fun searchForCity(searchTerm: String) = dao.findCitiesByName(searchTerm)
}