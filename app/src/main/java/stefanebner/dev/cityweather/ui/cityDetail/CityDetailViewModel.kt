package stefanebner.dev.cityweather.ui.cityDetail

import android.arch.lifecycle.ViewModel
import stefanebner.dev.cityweather.data.CityRepository

class CityDetailViewModel(
        private val repository: CityRepository
) : ViewModel() {

    fun getCityInformation(id: Int) = repository.getCityById(id)
}