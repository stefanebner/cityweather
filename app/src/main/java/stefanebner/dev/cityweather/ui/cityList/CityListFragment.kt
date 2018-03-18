package stefanebner.dev.cityweather.ui.cityList

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_city_list.*
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.model.City
import stefanebner.dev.cityweather.ui.cityDetail.CityDetailActivity
import stefanebner.dev.cityweather.utils.InjectorUtils


class CityListFragment: Fragment() {

    private lateinit var cityListAdapter: CityListAdapter
    private lateinit var searchListAdapter: CityListAdapter
    private lateinit var viewModelCity: CityListViewModel
    private var position = RecyclerView.NO_POSITION
    private lateinit var searchView: SearchView
    private lateinit var menuItem: MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_city_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        context?.let {
            val factory = InjectorUtils().provideListViewModelFactory(it)
            viewModelCity = ViewModelProviders.of(this as Fragment, factory)
                    .get(CityListViewModel::class.java)
        }

        recyclerview_cities.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_cities.setHasFixedSize(true)
        recyclerview_search.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_search.setHasFixedSize(true)

        val itemDecor = DividerItemDecoration(context, LinearLayout.VERTICAL)
        recyclerview_cities.addItemDecoration(itemDecor)
        recyclerview_search.addItemDecoration(itemDecor)

        cityListAdapter = CityListAdapter(context, emptyList()) { onCityClicked(it) }
        recyclerview_cities.adapter = cityListAdapter

        viewModelCity.getCities().observe(activity as LifecycleOwner, Observer {
            t: List<City>? -> kotlin.run {
                t?.let { cityListAdapter.updateCities(it) }
            }

            if (position == RecyclerView.NO_POSITION) {
                position = 0
            }

            recyclerview_cities.smoothScrollToPosition(position)
        })

        searchListAdapter = CityListAdapter(context, emptyList()) { onCityClicked(it) }
        recyclerview_search.adapter = searchListAdapter

        viewModelCity.getSearchedCities().observe(activity as LifecycleOwner, Observer {
            t: List<City>? -> kotlin.run {
                t?.let { searchListAdapter.updateCities(it) }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_search -> {
            putListIntoForeground(false)
            false
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menuItem = menu.findItem(R.id.action_search)
        searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                viewModelCity.searchForCity(query)
                return true
            }

            override fun onQueryTextSubmit(query: String) : Boolean {
                viewModelCity.updateWeatherForCity(query)
                return true
            }
        })

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                putListIntoForeground(true)
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem) = true
        })
    }

    private fun onCityClicked(id: Int) {
        putListIntoForeground(true)
        searchView.onActionViewCollapsed()
        menuItem.collapseActionView()
        viewModelCity.updateWeatherForId(id)
        val weatherDetailIntent = Intent(context, CityDetailActivity::class.java)
        weatherDetailIntent.putExtra(CityDetailActivity().cityId, id)
        startActivity(weatherDetailIntent)
    }

    private fun putListIntoForeground(city: Boolean) {
        recyclerview_cities.visibility = if (city) View.VISIBLE else View.INVISIBLE
        recyclerview_search.visibility = if (!city) View.VISIBLE else View.INVISIBLE
    }
}