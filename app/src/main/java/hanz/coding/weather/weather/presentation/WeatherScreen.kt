package hanz.coding.weather.weather.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hanz.coding.weather.R
import hanz.coding.weather.weather.domain.weather.WeatherData
import hanz.coding.weather.weather.domain.weather.WeatherTemperature
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Preview
@Composable
fun WeatherScreen(
    weatherState: WeatherState,
    modifier: Modifier = Modifier
) {
    val weatherData = weatherState.weatherInfo?.currentWeatherData ?: return
    val weatherTemp = weatherState.weatherInfo.weatherTemperature ?: return
    val tempMax = weatherTemp[0].tempMax
    val tempMin = weatherTemp[0].tempMin

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(android.graphics.Color.parseColor("#59469d")),
                        Color(android.graphics.Color.parseColor("#643d67"))
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = weatherData.weatherType.weatherDesc,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 48.dp),
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = painterResource(weatherData.weatherType.iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp)
                    )

                    Text(
                        text = weatherData.time.format(DateTimeFormatter.ofPattern("EEE MMM dd : hh:mm a")),
                        fontSize = 19.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = weatherData.temperatureCelsius.toString(),
                        fontSize = 63.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "H:$tempMax L$tempMin",
                        fontSize = 19.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                            .background(
                                color = colorResource(R.color.purple),
                                shape = RoundedCornerShape(25.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .height(120.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            WeatherDetailItem(R.drawable.rain, "${weatherData.rain} mm", "Rain")
                            WeatherDetailItem(
                                R.drawable.wind,
                                "${weatherData.windSpeed} km/h",
                                "Wind Speed"
                            )
                            WeatherDetailItem(
                                R.drawable.humidity,
                                "${weatherData.humidity} %",
                                "Humidity"
                            )
                        }
                    }

                    Text(
                        text = "Today",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                    )
                }
                val futureHourlyItems = weatherState.weatherInfo.futureHourlyData
                if (futureHourlyItems != null) {
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(futureHourlyItems) { item ->
                                FutureHourlyViewHolder(item)
                            }
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Future",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "Next 7 day>",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(weatherState.weatherInfo.weatherTemperature) { item ->
                    FutureItem(item)
                }
            }
        }
    }
}

@Composable
fun FutureItem(weatherTemperature: WeatherTemperature) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = weatherTemperature.time.format(DateTimeFormatter.ofPattern("EEE")),
            fontSize = 14.sp,
            color = Color.White,
        )

        Image(
            painter = painterResource(weatherTemperature.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 32.dp)
                .size(45.dp)
        )

        Text(
            text = weatherTemperature.weatherType.weatherDesc,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Text(
            text = "${weatherTemperature.tempMax}",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 16.dp)
        )
        Text(
            text = "${weatherTemperature.tempMin}",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 16.dp)
        )
    }
}

@Composable
fun getDrawableResourceId(picPath: String): Int {
    return when (picPath) {
        "cloudy" -> R.drawable.cloudy
        "sunny" -> R.drawable.sunny
        "wind" -> R.drawable.wind
        "rainy" -> R.drawable.rainy
        "storm" -> R.drawable.storm
        else -> R.drawable.sunny
    }
}

val futureItems = listOf(
    FutureModel(
        day = "Sat",
        picPath = "cloudy",
        status = "Cloudy",
        highTemp = 24,
        lowTemp = 12
    ),
    FutureModel(
        day = "Sat",
        picPath = "cloudy",
        status = "Cloudy",
        highTemp = 24,
        lowTemp = 12
    ),
    FutureModel(
        day = "Sat",
        picPath = "cloudy",
        status = "Cloudy",
        highTemp = 24,
        lowTemp = 12
    ),
    FutureModel(
        day = "Sat",
        picPath = "cloudy",
        status = "Cloudy",
        highTemp = 24,
        lowTemp = 12
    ),
    FutureModel(
        day = "Sat",
        picPath = "cloudy",
        status = "Cloudy",
        highTemp = 24,
        lowTemp = 12
    )
)

@Composable
fun FutureHourlyViewHolder(weatherData: WeatherData) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .background(
                color = colorResource(R.color.purple),
                shape = RoundedCornerShape(size = 8.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = weatherData.time.format(DateTimeFormatter.ofPattern("h a")),
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(
                weatherData.weatherType.iconRes
            ),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "${weatherData.temperatureCelsius}",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WeatherDetailItem(icon: Int, value: String, label: String) {
    Column(
        modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(34.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = label, color = Color.White, textAlign = TextAlign.Center
        )
    }
}