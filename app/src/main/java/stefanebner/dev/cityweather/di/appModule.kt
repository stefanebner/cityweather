package stefanebner.dev.cityweather.di


import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import stefanebner.dev.cityweather.data.CityRepository
import stefanebner.dev.cityweather.data.database.CityDatabase
import stefanebner.dev.cityweather.data.network.OpenWeatherDataSource
import stefanebner.dev.cityweather.ui.cityDetail.CityDetailViewModel
import stefanebner.dev.cityweather.ui.cityList.CityListViewModel

val cityModule = applicationContext {
    viewModel { CityListViewModel(get()) }
}

val detailModule = applicationContext {
    viewModel { params -> CityDetailViewModel(get(), params["id"]) }
}

val repositoryModule = applicationContext {
    bean { CityRepository(get(), get()) }
}

val dataSourceModule = applicationContext {
    bean { OpenWeatherDataSource(get(), get()) }
}

val databaseModule = applicationContext {
    bean { CityDatabase(get()) }
}

val allModlules = listOf(cityModule, detailModule, repositoryModule, dataSourceModule, databaseModule)
