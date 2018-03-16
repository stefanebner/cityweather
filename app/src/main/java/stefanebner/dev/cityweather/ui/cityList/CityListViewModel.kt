package stefanebner.dev.cityweather.ui.cityList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import org.jetbrains.anko.doAsync
import stefanebner.dev.cityweather.data.CityRepository
import stefanebner.dev.cityweather.model.City
import java.util.*

class CityListViewModel(
        private val repository: CityRepository
) : ViewModel() {

    // private val fourteenDaysInMs: Long = 14 * 1000 * 60 * 60 * 24
    private val observer: Observer<List<City>>
    private val repositoryCities: LiveData<List<City>> = repository.getAll()
    private val cities = MutableLiveData<List<City>>()
    private val searchedCities = MutableLiveData<List<City>>()

    init {
        observer = Observer { cities.postValue(it?.sortedWith(dateComparator))}
        repositoryCities.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        repositoryCities.removeObserver(observer)
    }

    fun updateWeatherForCity(name: String) {
        repository.updateWeatherForCity(name)
    }

    fun updateWeatherForId(id: Int) {
        repository.updateWeatherForId(id)
    }

    fun searchForCity(searchTerm: String) {
        doAsync {
            searchedCities.postValue(repository.searchForCity(searchTerm))
        }
    }

    fun getCities() : LiveData<List<City>> = cities

    fun getSearchedCities() : LiveData<List<City>> = searchedCities

    private val dateComparator: Comparator<City> = Comparator { c1, c2 ->
        if (c1.date == c2.date) {
            c1.name.compareTo(c2.name)
        } else {
            c1.date.compareTo(c2.date)
        }
    }

    private val nameComparator: Comparator<City> = Comparator { c1, c2 ->
        if (c1.name == c2.name) {
            c1.date.compareTo(c2.date)
        } else {
            c1.name.compareTo(c2.name)
        }
    }
}