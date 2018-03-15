package stefanebner.dev.cityweather.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

// Ignore all Entries for now, need typeconverter to fit into DB
// TODO Write typeconverter for Main/Weather or deserialize differently from json
@Entity(tableName = "weatherEntries")
data class WeatherEntry(
        @Ignore
        var coordinates: Coordinates? = null,
        @Ignore
        var weather: Weather? = null,
        @Ignore
        var base: String = "",
        @Ignore
        var main: Main? = null,
        @Ignore
        var visibility: Double = 0.0,
        @Ignore
        var wind: Wind? = null,
        @Ignore
        var clouds: Clouds? = null,
        @Ignore
        var dt: Long = 0,
        @Ignore
        var sys: Sys? = null,
        @PrimaryKey
        var id: Int = 0,
        var name: String = "",
        @Ignore
        var cod: Int = 0
)

data class Coordinates(
        val lon: Double,
        val lat: Double
)

data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
)

data class Main(
        val temp: Double,
        val prepssure: Int,
        val humidity: Int,
        val tempMin: Double,
        val tempMax: Double
)

data class Wind(
        val speed: Double,
        val deg: Int
)

data class Clouds(
        val all: Int
)

data class Sys(
        val type: Int,
        val id: Int,
        val message: Double,
        val country: String,
        val sunrise: Long,
        val sunset: Long
)