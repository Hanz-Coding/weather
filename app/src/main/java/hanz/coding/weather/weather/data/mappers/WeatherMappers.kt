package hanz.coding.weather.weather.data.mappers

import android.annotation.SuppressLint
import hanz.coding.weather.weather.data.remote.WeatherDailyDto
import hanz.coding.weather.weather.data.remote.WeatherDto
import hanz.coding.weather.weather.data.remote.WeatherResponseDto
import hanz.coding.weather.weather.domain.weather.WeatherData
import hanz.coding.weather.weather.domain.weather.WeatherInfo
import hanz.coding.weather.weather.domain.weather.WeatherTemperature
import hanz.coding.weather.weather.domain.weather.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexWeatherData(
    val index: Int,
    val data: WeatherData
)

@SuppressLint("NewApi")
fun WeatherDto.toDomain(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = this.temperature[index]
        val pressure = this.pressure[index]
        val windSpeed = this.windSpeed[index]
        val humidity = this.humidity[index]
        val rain = this.rain[index]
        val weatherType = WeatherType.fromWMO(this.weatherCode[index])
        val weatherTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)
        IndexWeatherData(
            index = index,
            data = WeatherData(
                time = weatherTime,
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                rain = rain,
                weatherType = weatherType
            )
        )
    }.groupBy { it.index / 24 }
        .mapValues { it.value.map { it.data } }
}

private fun WeatherDailyDto.toDomain(): List<WeatherTemperature> {
    return time.mapIndexed { index, time ->
        val tempMax = temperatureMax[index]
        val tempMin = temperatureMin[index]
        val weatherType = WeatherType.fromWMO(weatherCode[index])
        val weatherTime = LocalDate.parse(time, DateTimeFormatter.ISO_LOCAL_DATE)
        WeatherTemperature(
            time = weatherTime,
            weatherType = weatherType,
            tempMax = tempMax,
            tempMin = tempMin
        )
    }
}


@SuppressLint("NewApi")
fun WeatherResponseDto.toWeatherInfo(): WeatherInfo {
    println("hanz th ${this}")
    val weatherDataMap = this.weatherDto.toDomain()
    val now = LocalDateTime.now()
    val currentData = weatherDataMap[0]
    if (currentData != null) {
        val currentWeatherData = weatherDataMap[0]?.find {
            val hour = if (now.minute < 30) now.hour else now.hour + 1
            it.time.hour == hour
        }
        val index = currentData.indexOf(currentWeatherData)

        val futureHourlyData =
            currentData.subList(fromIndex = index, toIndex = currentData.size - 1)

        return WeatherInfo(
            weatherDataPerDay = this.weatherDto.toDomain(),
            currentWeatherData = currentWeatherData,
            futureHourlyData = futureHourlyData,
            weatherTemperature = this.weatherDailyDto.toDomain()
        )
    } else return WeatherInfo(
        weatherDataPerDay = this.weatherDto.toDomain(),
        currentWeatherData = null,
        futureHourlyData = null,
        weatherTemperature = null
    )
}