package stefanebner.dev.cityweather.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import stefanebner.dev.cityweather.data.database.CityDatabase
import stefanebner.dev.cityweather.data.network.OpenWeatherDataSource
import stefanebner.dev.cityweather.model.City
import java.io.InputStream

class CityRepository(
       database: CityDatabase,
       private val dataSourceOpen: OpenWeatherDataSource
) {
    private val dao = database.cityDatabase.cityDao()

    // id nm lat lon countryCode ->
    // city ID, Name, latitude, longitude, 2letter country code
    // currently just over 74k cities in the list
    fun startLocalCitySync(inputStream: InputStream) {
        Observable.just(dao)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    dao -> if (dao.getNumberOfEntries() < 74000) parseLocalFiles(inputStream)
                }
    }

    private fun parseLocalFiles(inputStream: InputStream) {
        parseFile(inputStream)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { dao.insertCity(City(it.split("\t"))) }
    }

    private fun parseFile(inputStream: InputStream) = Observable.create<String> { e ->
        inputStream.bufferedReader().forEachLine { line -> e.onNext(line) }
        e.onComplete()
    }

    fun getAll() = dao.getAll()

    fun getCityById(id: Int) = dao.getWeatherForId(id)

    private fun getNumberOfCitiesInDatabase() = dao.getNumberOfEntries()

    fun updateWeatherForCity(name: String) = dataSourceOpen.updateCityData(name)

    fun updateWeatherForId(id: Int) = dataSourceOpen.updateCityData(id)

    fun searchForCity(searchTerm: String) = dao.findCitiesByName(searchTerm)
}