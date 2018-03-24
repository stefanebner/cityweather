package stefanebner.dev.cityweather

import android.app.Application
import org.koin.android.ext.android.startKoin
import stefanebner.dev.cityweather.di.appModule

open class CityWeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}