package stefanebner.dev.cityweather.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
        @PrimaryKey
        val id: Int ,
        val name: String,
        val country: String,
        val lon: Double,
        val lat: Double,
        val temp: Double = 0.0,
        val date: Long = 0,
        val tempMin: Double = 0.0,
        val tempMax: Double = 0.0,
        val pressure: Int = 0,
        val humidity: Int = 0,
        val wind: Double = 0.0,
        val cloudiness: Int = 0,
        var description: String = "",
        var icon: String = "",
        var weatherCode: Int = 0
) {
    // Used when creating a city from the parsed json we get back from OpenWeather
    constructor(entry: WeatherEntry) : this(
        entry.id,
        entry.name,
        entry.sys.country,
        entry.coord.lon,
        entry.coord.lat,
        entry.main.temp,
        entry.dt,
        entry.main.tempMin,
        entry.main.tempMax,
        entry.main.pressure,
        entry.main.humidity,
        entry.wind.speed,
        entry.clouds.all
    ) {
        entry.weather?.let {
            if (it.isNotEmpty()) {
                description = it[0].description
                icon = it[0].icon
                weatherCode = it[0].id
            }
        }
    }

    // Used by parsing from the textfile containing all the cities
    constructor(parsedLine: List<String>) : this(
        parsedLine[0].toInt(),
        parsedLine[1],
        parsedLine[4],
        parsedLine[2].toDouble(),
        parsedLine[3].toDouble()
    )
}