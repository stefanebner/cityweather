package stefanebner.dev.cityweather.ui.cityList

import android.app.SearchManager
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_city_list.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.appcompat.v7.coroutines.onSearchClick
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.data.CityRepository
import stefanebner.dev.cityweather.model.City
import stefanebner.dev.cityweather.ui.cityDetail.CityDetailActivity
import stefanebner.dev.cityweather.utils.showView

class MainActivity : AppCompatActivity() {

    private lateinit var cityListAdapter: CityListAdapter
    private lateinit var searchListAdapter: CityListAdapter
    private val viewModelCity by viewModel<CityListViewModel>()
    private lateinit var searchView: SearchView
    private lateinit var menuItem: MenuItem
    private var isSearchOpen = false
    private val repository: CityRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(activity_toolbar)

        savedInstanceState ?: kotlin.run {
            repository.startLocalCitySync(assets.open("cityList.txt"))
        }

        val itemDecor = DividerItemDecoration(this, LinearLayout.VERTICAL)
        cityListAdapter = CityListAdapter(this, emptyList()) { onCityClicked(it) }
        searchListAdapter = CityListAdapter(this, emptyList()) { onCityClicked(it) }
        setupViewAndAdapter(itemDecor, recycler_cities, cityListAdapter, viewModelCity.getCities())
        setupViewAndAdapter(itemDecor, recycler_search, searchListAdapter, viewModelCity.getSearchedCities())
    }

    private fun setupViewAndAdapter(itemDecor: DividerItemDecoration, view: RecyclerView, adapter: CityListAdapter,
                                    databaseData: LiveData<List<City>>) {
        view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        view.setHasFixedSize(true)
        view.addItemDecoration(itemDecor)
        view.adapter = adapter

        databaseData.observe(this as LifecycleOwner, Observer {
            t: List<City>? -> kotlin.run { t?.let { adapter.updateCities(it) } }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        menuItem = menu.findItem(R.id.action_search)
        searchView = menuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.onSearchClick {
            putCityListIntoForeground(false)
            isSearchOpen = true
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()) {
                    searchListAdapter.updateCities(emptyList())
                } else {
                    viewModelCity.searchForCity(query)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String) : Boolean {
                viewModelCity.updateWeatherForCity(query)
                return true
            }
        })

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                putCityListIntoForeground(true)
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem) = true
        })
        return true
    }

    private fun onCityClicked(id: Int) {
        collapseSearch()
        viewModelCity.updateWeatherForId(id)
        val weatherDetailIntent = Intent(this, CityDetailActivity::class.java)
        weatherDetailIntent.putExtra(CityDetailActivity().cityId, id)
        startActivity(weatherDetailIntent)
    }

    private fun putCityListIntoForeground(showCities: Boolean) {
        recycler_cities.showView(showCities)
        recycler_search.showView(!showCities)
    }

    private fun collapseSearch() {
        isSearchOpen = false
        putCityListIntoForeground(true)
        searchView.onActionViewCollapsed()
        menuItem.collapseActionView()

    }

    override fun onBackPressed() {
        if (isSearchOpen) {
            collapseSearch()
            return
        }
        super.onBackPressed()
    }
}
