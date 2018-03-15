package stefanebner.dev.cityweather.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import stefanebner.dev.cityweather.model.WeatherEntry

@Database(entities = [WeatherEntry::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao() : WeatherDao

    companion object {
        @Volatile private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context?): WeatherDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context?) =
                if (context != null) {
                    Room.databaseBuilder(context.applicationContext,
                            WeatherDatabase::class.java, "WeatherDatabase.db").build()
                } else {
                    throw Throwable()
                }
    }

}