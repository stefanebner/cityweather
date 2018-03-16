package stefanebner.dev.cityweather.ui.cityDetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import stefanebner.dev.cityweather.data.CityRepository

class CityDetailViewModel(
        repository: CityRepository,
        id: Int

) : ViewModel() {

    private val city = repository.getCityById(id)

    fun getCityInformation() = city
}