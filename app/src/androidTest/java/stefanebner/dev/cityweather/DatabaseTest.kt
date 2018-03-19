package stefanebner.dev.cityweather

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import stefanebner.dev.cityweather.data.database.CityDao
import stefanebner.dev.cityweather.data.database.CityDatabase
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private var mDao: CityDao? = null
    private var mDb: CityDatabase? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, CityDatabase::class.java!!).build()
        mDao = mDb!!.cityDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCityAndGetByName() {
        val city =createCity(1)
        mDao!!.insertCity(city)
        val byName = mDao!!.getWeatherForCity("name1").blockingObserve()
        assertThat(byName, equalTo(city))
    }

    @Test
    @Throws(Exception::class)
    fun writeCityAndGetById() {
        val city = createCity(1)
        mDao!!.insertCity(city)
        val byName = mDao!!.getWeatherForId(1).blockingObserve()
        assertThat(byName, equalTo(city))
    }
}