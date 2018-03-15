package stefanebner.dev.cityweather.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "WeatherEntry")
class WeatherEntry {
    @PrimaryKey
    var id: Int = 0
}