package hanz.coding.weather.weather.data.remote

import com.squareup.moshi.Json

data class WeatherResponseDto(
    @field:Json(name = "hourly")
    val weatherDto: WeatherDto,
    @field:Json(name = "daily")
    val weatherDailyDto: WeatherDailyDto
)
