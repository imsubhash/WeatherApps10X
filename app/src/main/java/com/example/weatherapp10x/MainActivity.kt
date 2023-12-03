package com.example.weatherapp10x

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp10x.databinding.ActivityMainBinding
import com.example.weatherapp10x.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val nextFourDays = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNextFourDays()

        viewModel.weatherResponse.observe(this) { weather ->
            Log.d("Subhash", weather.toString())
            binding.apply {
                tvCityName.text = weather.name
                tvTemperature.text = kelvinToCelsius(weather.main.temp)
            }

        }

        viewModel.forecastResponse.observe(this) { forecastResponse ->
            binding.apply {
                val forecast = forecastResponse.list
                forecast1.tvDay.text = nextFourDays[0]
                forecast1.tvForecast.text = kelvinToCelsius(forecast[0].main.temp)

                forecast2.tvDay.text = nextFourDays[1]
                forecast2.tvForecast.text = kelvinToCelsius(forecast[1].main.temp)

                forecast3.tvDay.text = nextFourDays[2]
                forecast3.tvForecast.text = kelvinToCelsius(forecast[2].main.temp)

                forecast4.tvDay.text = nextFourDays[3]
                forecast4.tvForecast.text = kelvinToCelsius(forecast[3].main.temp)
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.parentView.visibility = View.GONE
                binding.loadingIndicator.visibility = View.VISIBLE
            } else {
                binding.parentView.visibility = View.VISIBLE
                binding.loadingIndicator.visibility = View.GONE
            }
        }

        viewModel.isFailed.observe(this) { isFailed ->
            if (isFailed) {
                Toast.makeText(this, "Something went wring", Toast.LENGTH_LONG).show()
                binding.parentView.visibility = View.GONE
                binding.loadingIndicator.visibility = View.VISIBLE
            } else {
                binding.parentView.visibility = View.VISIBLE
                binding.loadingIndicator.visibility = View.GONE
            }
        }


    }

    private fun kelvinToCelsius(kelvin: Double): String {
        val celsius = kelvin - 273.15
        return String.format("%.0f°C", celsius)
    }

    private fun setNextFourDays() {
        val today = LocalDate.now()
        for (i in 1..4) {
            val nextDay = today.plusDays(i.toLong())
            val dayName = nextDay.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            nextFourDays.add(dayName)
        }
    }

}