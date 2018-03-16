package stefanebner.dev.cityweather.ui.cityDetail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import stefanebner.dev.cityweather.data.CityRepository

@Suppress("UNCHECKED_CAST")
class CityDetailViewModelFactory(
        private val repository: CityRepository,
        private val id: Int
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return CityDetailViewModel(repository, id) as T
    }
}