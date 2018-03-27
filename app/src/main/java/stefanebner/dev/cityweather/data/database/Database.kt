package stefanebner.dev.cityweather.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import stefanebner.dev.cityweather.model.City

@Database(entities = [City::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun cityDao() : CityDao
}