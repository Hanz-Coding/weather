package hanz.coding.weather.weather.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val futureHourlyData : List<WeatherData>?,
    val weatherTemperature: List<WeatherTemperature>?
)
