package stefanebner.dev.cityweather.ui.cityList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.city_list_item.view.*
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.model.City
import java.text.SimpleDateFormat
import java.util.*

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
            item_city.text = city.name
            item_temp.text = resources.getText(R.string.temperature, city.temp.toString())
            item_country.text = city.country

            if (city.date > 1) {
                val sdf = SimpleDateFormat("EEE, d MMM - HH:mm:ss", resources.configuration.locales[0])
                item_date.text = sdf.format(Date(city.date * 1000L))
            } else {
                item_date.text = context.getString(R.string.not_synced)
            }

            setOnClickListener { listener(city.id) }
        }
    }

    fun updateCities(updatedCities: List<City>) {
        cities = updatedCities
        notifyDataSetChanged()
    }
}
