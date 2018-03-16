package stefanebner.dev.cityweather.data.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import org.jetbrains.anko.doAsync
import stefanebner.dev.cityweather.BuildConfig
import stefanebner.dev.cityweather.model.City

class OpenWeatherDataSource private constructor(val context: Context) {

    private val cities: MutableLiveData<List<City>> = MutableLiveData()

    companion object {
        @Volatile private var INSTANCE: OpenWeatherDataSource? = null

        fun getInstance(context: Context) : OpenWeatherDataSource =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: createDataSource(context).also { INSTANCE = it}
                }

        private fun createDataSource(context: Context) : OpenWeatherDataSource =
                OpenWeatherDataSource(context)
    }

    fun getAllCities(): LiveData<List<City>> = cities

    fun syncWeatherEntries() {
        //TODO make plan how to slowly sync all data, starting with searched cities first
    }

    fun syncCity(name: String) {
        doAsync {
            val response: String? = NetworkUtils().
                    getJsonResponse(NetworkUtils().getCityByUrl(name, "", BuildConfig.OpenWeatherApiKey))
//            cities.postValue()
        }
    }
}