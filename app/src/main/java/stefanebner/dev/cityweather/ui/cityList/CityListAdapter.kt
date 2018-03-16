package stefanebner.dev.cityweather.ui.cityList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.city_list_item.view.*
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.model.City

class CityListAdapter(
        private val context: Context?,
        private var cities: List<City>,
        private val listener: (Int) -> Unit
) : RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CityViewHolder(LayoutInflater.from(context).inflate(R.layout.city_list_item, parent, false))

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) =
            holder.bind(cities[position], listener)

    override fun getItemCount() = cities.size

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: City, listener: (Int) -> Unit) = with(itemView) {
            item_name.text = city.name
            item_temp.text = city.temp.toString()

            setOnClickListener { listener(city.id) }
        }
    }

    fun updateCities(updatedCities: List<City>) {
        cities = updatedCities
        notifyDataSetChanged()
    }
}
