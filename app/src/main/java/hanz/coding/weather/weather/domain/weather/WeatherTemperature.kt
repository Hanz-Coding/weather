package hanz.coding.weather.weather.domain.weather

import java.time.LocalDate

data class WeatherTemperature(
    val time: LocalDate,
    val weatherType: WeatherType,
    val tempMax: Double,
    val tempMin: Double
)
