package hanz.coding.weather.weather.domain

import hanz.coding.weather.core.domain.util.NetworkError
import hanz.coding.weather.core.domain.util.Result
import hanz.coding.weather.weather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(
        longitude: Double,
        latitude: Double
    ): Result<WeatherInfo, NetworkError>
}