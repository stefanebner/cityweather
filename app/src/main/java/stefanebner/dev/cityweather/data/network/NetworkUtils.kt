package stefanebner.dev.cityweather.data.network

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class NetworkUtils {
    private val cityByName: String = "http://api.openweathermap.org/data/2.5/weather?q="
    private val cityById: String = "http://api.openweathermap.org/data/2.5/weather?id="
    private val appId: String = "&APPID="
    private val asCelsius: String = "&units=metric"

    fun getCityByUrl(name: String, country: String = "", apiKey: String) =
            cityByName + name + (if (country.isNotBlank()) ",$country" else "") +
                    appId + apiKey + asCelsius

    fun getCityById(id: Int, country: String = "", apiKey: String) =
            cityById + id.toString() + (if (country.isNotBlank()) ",$country" else "") +
                    appId + apiKey + asCelsius

    @Throws(IOException::class)
    fun getJsonResponse(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return response?.body()?.string()
    }
}