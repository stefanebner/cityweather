package stefanebner.dev.cityweather.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import stefanebner.dev.cityweather.model.WeatherEntry

class JsonParser {
    fun parseWeatherFromJson(json: String): WeatherEntry {
        val type = object : TypeToken<WeatherEntry>() {}.type
        return Gson().fromJson<WeatherEntry>(json, type)
    }
}