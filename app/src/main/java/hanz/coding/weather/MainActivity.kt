package hanz.coding.weather

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hanz.coding.weather.weather.presentation.WeatherScreen
import hanz.coding.weather.ui.theme.WeatherTheme
import hanz.coding.weather.weather.presentation.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            enableEdgeToEdge()
            setContent {
                val viewModel = koinViewModel<WeatherViewModel>()
                viewModel.loadWeatherInfo()
                val weatherState by viewModel.state.collectAsStateWithLifecycle()
                WeatherTheme {
                    WeatherScreen(weatherState)
                }
            }
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

    }
}
