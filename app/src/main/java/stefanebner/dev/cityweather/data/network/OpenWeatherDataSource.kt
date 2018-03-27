package stefanebner.dev.cityweather.data.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import io.reactivex.schedulers.Schedulers
import stefanebner.dev.cityweather.BuildConfig
import stefanebner.dev.cityweather.data.database.CityDao
import stefanebner.dev.cityweather.data.database.CityDatabase
import stefanebner.dev.cityweather.model.City

class OpenWeatherDataSource(
        val context: Context,
        database: CityDatabase
) {

    private val dao: CityDao = database.cityDatabase.cityDao()
    private val cities: MutableLiveData<List<City>> = MutableLiveData()
    private val asCelsius: String = "metric"
    private val restApi = RestApi()

    fun getAllCities(): LiveData<List<City>> = cities

    fun updateCityData(url: String) {
        restApi.getWeatherForCity(url, BuildConfig.OpenWeatherApiKey, asCelsius)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe { weatherEntry ->
                    dao.insertCity(City(weatherEntry))
                }
    }

    fun updateCityData(id: Int) {
        restApi.getWeatherForCity(id, BuildConfig.OpenWeatherApiKey, asCelsius)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe { weatherEntry ->
                    dao.insertCity(City(weatherEntry))
                }
    }

    fun syncWeatherEntries() {
        //TODO OPTIONAL: make plan how to slowly sync all data, starting with searched cities first
    }
}