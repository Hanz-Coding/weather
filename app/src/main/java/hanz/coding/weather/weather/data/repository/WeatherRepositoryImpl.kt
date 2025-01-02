package hanz.coding.weather.weather.data.repository

import hanz.coding.weather.core.data.networking.safeCall
import hanz.coding.weather.core.domain.util.NetworkError
import hanz.coding.weather.core.domain.util.Result
import hanz.coding.weather.core.domain.util.map
import hanz.coding.weather.weather.data.mappers.toWeatherInfo
import hanz.coding.weather.weather.data.remote.WeatherApi
import hanz.coding.weather.weather.data.remote.WeatherResponseDto
import hanz.coding.weather.weather.domain.WeatherRepository
import hanz.coding.weather.weather.domain.weather.WeatherInfo

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(
        longitude: Double,
        latitude: Double
    ): Result<WeatherInfo, NetworkError> {
        return try {
            safeCall<WeatherResponseDto> {
                weatherApi.getWeather(longitude, latitude)
            }.map { responseDto -> responseDto.toWeatherInfo() }
        } catch (
            e: Exception
        ) {
            println("hanz error ${e.message} ")
            e.printStackTrace()
            return Result.Error(NetworkError.UNKNOWN)
        }
    }
}