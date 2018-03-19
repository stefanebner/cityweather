package stefanebner.dev.cityweather.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import stefanebner.dev.cityweather.model.City

@Database(entities = [City::class], version = 1)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao() : CityDao

    companion object {
        @Volatile private var INSTANCE: CityDatabase? = null

        fun getInstance(context: Context?): CityDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context?) =
                if (context != null) {
                    Room.databaseBuilder(context.applicationContext,
                            CityDatabase::class.java, "WeatherDatabase.db")
                            .fallbackToDestructiveMigration() //TODO provide migration
                            .build()
                } else {
                    throw Throwable()
                }
    }

}