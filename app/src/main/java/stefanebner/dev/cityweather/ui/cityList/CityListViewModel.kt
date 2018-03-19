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

    private val observer: Observer<List<City>>
    private val repositoryCities: LiveData<List<City>> = repository.getAll()
    private val cities = MutableLiveData<List<City>>()
    private val searchedCities = MutableLiveData<List<City>>()
    private var currentSearchTermLength = 0

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
        currentSearchTermLength = searchTerm.length
        doAsync {
            val foundCities = repository.searchForCity(searchTerm).sortedWith(dateComparator)
            if(currentSearchTermLength == searchTerm.length) {
                searchedCities.postValue(foundCities)
            }
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
}