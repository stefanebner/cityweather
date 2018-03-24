package stefanebner.dev.cityweather

import org.junit.After
import org.junit.Test
import org.koin.android.ext.koin.with
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun
import org.mockito.Mockito.mock
import stefanebner.dev.cityweather.di.appModule

class DryRunTest : KoinTest {

    @Test
    fun testDryRun() {
        startKoin(listOf(appModule)) with (mock(CityWeatherApplication::class.java))
        dryRun()
    }

    @After
    fun stop() {
        closeKoin()
    }
}