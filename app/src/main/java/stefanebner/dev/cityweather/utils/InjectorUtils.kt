package stefanebner.dev.cityweather.utils

import android.content.Context
import stefanebner.dev.cityweather.data.CityRepository
import stefanebner.dev.cityweather.data.database.CityDatabase
import stefanebner.dev.cityweather.data.network.OpenWeatherDataSource
import stefanebner.dev.cityweather.ui.cityDetail.CityDetailViewModelFactory
import stefanebner.dev.cityweather.ui.cityList.CityListViewModelFactory

class InjectorUtils {

    fun provideRepository(context: Context) : CityRepository {
        val dataBase = CityDatabase.getInstance(context)
        val dataSource = OpenWeatherDataSource.getInstance(context, dataBase.cityDao())
        return CityRepository.getInstance(dataBase.cityDao(), dataSource)
    }

    fun provideListViewModelFactory(applicationContext: Context) =
            CityListViewModelFactory(provideRepository(applicationContext))

    fun provideDetailViewModelFactory(applicationContext: Context, id: Int) =
            CityDetailViewModelFactory(provideRepository(applicationContext), id)
}