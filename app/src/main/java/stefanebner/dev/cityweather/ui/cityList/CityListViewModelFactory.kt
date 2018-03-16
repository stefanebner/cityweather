package stefanebner.dev.cityweather.ui.cityList

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import stefanebner.dev.cityweather.data.CityRepository

@Suppress("UNCHECKED_CAST")
class CityListViewModelFactory(
        private val repository: CityRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return CityListViewModel(repository) as T
    }
}