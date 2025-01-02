package hanz.coding.weather.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hanz.coding.weather.core.domain.util.Result
import hanz.coding.weather.weather.domain.WeatherRepository
import hanz.coding.weather.weather.domain.location.LocationTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    val weatherRepository: WeatherRepository,
    val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    fun loadWeatherInfo() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = false) }

            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    weatherRepository.getWeatherData(location.latitude, location.longitude).also {
                        println("hanz weatherInfo longitude ${location.longitude } latitude ${location.latitude}")
                    }) {
                    is Result.Success -> {
                        println("hanz weatherInfo why success")
                        _state.update {
                            it.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                error = null
                            ).also {
                                println("hanz weatherInfo ${result.data}")
                            }
                        }
                    }

                    is Result.Error -> {
                        println("hanz weatherInfo why Error")
                        _state.update {
                            it.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.error.name
                            )
                        }.also {
                            println("hanz weatherInfo Error")
                        }
                    }
                }
            } ?: run {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    ).also {
                        println("hanz weatherInfo run Error")
                    }
                }
            }
        }
    }
}