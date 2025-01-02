package hanz.coding.weather.weather.data.remote

import com.squareup.moshi.Json

data class WeatherDailyDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m_max")
    val temperatureMax: List<Double>,
    @field:Json(name = "temperature_2m_min")
    val temperatureMin: List<Double>,
    @field:Json(name = "weather_code")
    val weatherCode: List<Int>

)