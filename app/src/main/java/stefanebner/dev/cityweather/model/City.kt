package stefanebner.dev.cityweather.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

// TODO write typeconverter for relevant items
@Entity(tableName = "cities")
data class City(
        @PrimaryKey
        val id: Int ,
        val name: String,
        val country: String,
        val lon: Double,
        val lat: Double,
        val temp: Double,
        val date: Long,
        val requested: Boolean,
        val tempMin: Double,
        val tempMax: Double,
        val description: String,
        val pressure: Int,
        val humidity: Int,
        val wind: Double
)