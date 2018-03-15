package stefanebner.dev.cityweather.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import stefanebner.dev.cityweather.model.WeatherEntry

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherEntries")
    fun getAll(): List<WeatherEntry>

    @Query("SELECT * FROM weatherEntries WHERE name LIKE '%' || :cityName || '%'")
    fun getWeatherForCity(cityName: String): List<WeatherEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weather: List<WeatherEntry>?)

    @Query("DELETE FROM weatherEntries")
    fun clearAll()
}