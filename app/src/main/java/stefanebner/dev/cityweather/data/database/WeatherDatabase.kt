package stefanebner.dev.cityweather.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

}