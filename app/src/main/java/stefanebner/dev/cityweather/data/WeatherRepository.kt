package stefanebner.dev.cityweather.data

import stefanebner.dev.cityweather.data.database.WeatherDao
import stefanebner.dev.cityweather.data.network.WeatherNetworkDataSource

class WeatherRepository private constructor(
       private val dao: WeatherDao,
       private val dataSource: WeatherNetworkDataSource
) {

    init {
        // observe livedata here
    }

    companion object {
        @Volatile private var INSTANCE: WeatherRepository? = null

        fun getInstance(dao: WeatherDao, dataSource: WeatherNetworkDataSource) : WeatherRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: createWeatherRepository(dao, dataSource).also { INSTANCE = it}
                }

        private fun createWeatherRepository(dao: WeatherDao, dataSource: WeatherNetworkDataSource) =
                WeatherRepository(dao, dataSource)
    }

    fun getAll() = dao.getAll()

    fun clearAll() = dao.clearAll()

    fun insertAll() = dao.insertAll(null)

    fun getWeatherForCity(name: String) = dao.getWeatherForCity(name)
}