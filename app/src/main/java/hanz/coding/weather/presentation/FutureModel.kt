package hanz.coding.weather.presentation

data class FutureModel(
    val day: String,
    val picPath: String,
    val status: String,
    val highTemp: Int,
    val lowTemp: Int
)
