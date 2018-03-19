package stefanebner.dev.cityweather

import stefanebner.dev.cityweather.model.City

open class TestUtil {
    fun createCity(id: Int) = City(id, "name$id", "c$id",0.0, 0.0)
}