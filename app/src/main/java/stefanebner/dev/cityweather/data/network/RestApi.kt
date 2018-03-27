package stefanebner.dev.cityweather.data.network

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import stefanebner.dev.cityweather.model.WeatherEntry

class RestApi {
    private val openWeatherApi: OpenWeatherApi
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    fun getWeatherForCity(id: Int, apiKey: String, metric: String): Observable<WeatherEntry> =
            openWeatherApi.getWeatherById(id, apiKey, metric)

    fun getWeatherForCity(city: String, apiKey: String, metric: String): Observable<WeatherEntry> =
            openWeatherApi.getWeatherByName(city, apiKey, metric)
}