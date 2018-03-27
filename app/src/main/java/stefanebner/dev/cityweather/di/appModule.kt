package stefanebner.dev.cityweather.di


import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import stefanebner.dev.cityweather.data.CityRepository
import stefanebner.dev.cityweather.data.database.CityDatabase
import stefanebner.dev.cityweather.data.network.OpenWeatherDataSource
import stefanebner.dev.cityweather.data.network.RestApi
import stefanebner.dev.cityweather.ui.cityDetail.CityDetailViewModel
import stefanebner.dev.cityweather.ui.cityList.CityListViewModel

val appModule = applicationContext {
    viewModel { CityListViewModel(get()) }
    viewModel { CityDetailViewModel(get()) }
    bean { CityRepository(get(), get()) }
    bean { OpenWeatherDataSource(get(), get()) }
    bean { RestApi() }
    bean { CityDatabase(get()) }
}
