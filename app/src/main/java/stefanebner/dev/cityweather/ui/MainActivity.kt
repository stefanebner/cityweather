package stefanebner.dev.cityweather.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import stefanebner.dev.cityweather.BuildConfig
import stefanebner.dev.cityweather.R
import stefanebner.dev.cityweather.data.network.NetworkUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAsync {
            var json = NetworkUtils().getJsonResponse(NetworkUtils().getCityUrl("London", "",
                    BuildConfig.OpenWeatherApiKey))
            uiThread {
                singleWeatherText.text = json
            }
        }
    }
}
