package stefanebner.dev.cityweather.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import stefanebner.dev.cityweather.model.City

@Dao
interface CityDao {
    @Query("SELECT * FROM cities WHERE date > 0")
    fun getAll(): LiveData<List<City>>

    @Query("SELECT * FROM cities WHERE name LIKE :cityName || '%'")
    fun findCitiesByName(cityName: String): List<City>

    @Query("SELECT * FROM cities WHERE id IS :id")
    fun getWeatherForId(id: Int): LiveData<City>

    @Query("SELECT * FROM cities WHERE name IS :cityName")
    fun getWeatherForCity(cityName: String): LiveData<City>

    @Query("SELECT COUNT(*) FROM cities")
    fun getNumberOfEntries(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cities: List<City>?)

    @Query("DELETE FROM cities")
    fun clearAll()
}