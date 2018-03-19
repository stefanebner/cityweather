package stefanebner.dev.cityweather

import org.junit.After
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import stefanebner.dev.cityweather.data.database.CityDao
import stefanebner.dev.cityweather.data.database.CityDatabase
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private var mUserDao: CityDao? = null
    private var mDb: CityDatabase? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, CityDatabase::class.java!!).build()
        mUserDao = mDb!!.cityDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val city = TestUtil().createCity(1)
        mUserDao!!.insertCity(city)
        val byName = mUserDao!!.getWeatherForCity("name1")
        assertThat(byName.value, equalTo(city))
    }
}