package stefanebner.dev.cityweather.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.ui.cityList.CityListFragment
import stefanebner.dev.cityweather.utils.InjectorUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        savedInstanceState ?: kotlin.run {
            InjectorUtils().provideRepository(this).startLocalCitySync(assets.open("cityList.txt"))

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, CityListFragment() as Fragment, "cityList")
                    .commit()
        }
    }
}
