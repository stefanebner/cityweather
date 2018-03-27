package stefanebner.dev.cityweather.data.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import stefanebner.dev.cityweather.model.WeatherEntry

interface OpenWeatherApi {
    @GET("weather?")
    fun getWeatherById(@Query("id") id: Int,
                       @Query("APPID") appId: String,
                       @Query("units") metric: String) : Observable<WeatherEntry>

    @GET("weather?")
    fun getWeatherByName(@Query("q") city: String,
                         @Query("APPID") appId: String,
                         @Query("units") metric: String) : Observable<WeatherEntry>
}