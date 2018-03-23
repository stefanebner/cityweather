package stefanebner.dev.cityweather.data.database

import android.arch.persistence.room.Room
import android.content.Context

class CityDatabase(context: Context) {
    val cityDatabase by lazy { Room.databaseBuilder(context,
            Database::class.java, "WeatherDatabase.db")
            .fallbackToDestructiveMigration() //TODO provide migration
            .build() }
}