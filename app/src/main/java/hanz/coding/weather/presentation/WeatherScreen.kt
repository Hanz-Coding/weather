package hanz.coding.weather.presentation

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

@Preview
@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
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
                        text = "Mostly Cloudy",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 48.dp),
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = painterResource(R.drawable.cloudy),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp)
                    )

                    Text(
                        text = "Mon June 17 | 10:00 AM",
                        fontSize = 19.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "25",
                        fontSize = 63.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "H:27 L18",
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
                                .height(100.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            WeatherDetailItem(R.drawable.rain, "22%", "Rain")
                            WeatherDetailItem(R.drawable.wind, "22%", "Wind Speed")
                            WeatherDetailItem(R.drawable.humidity, "18%", "Humidity")
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
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(items) { item ->
                            FutureHourlyViewHolder(item)
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
                items(futureItems) { item ->
                    FutureItem(item)
                }
            }
        }
    }
}

@Composable
fun FutureItem(futureItem: FutureModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = futureItem.day,
            fontSize = 14.sp,
            color = Color.White,
        )

        Image(
            painter = painterResource(getDrawableResourceId(futureItem.picPath)),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 32.dp)
                .size(45.dp)
        )

        Text(
            text = futureItem.status,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Text(
            text = "${futureItem.highTemp}",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 16.dp)
        )
        Text(
            text = "${futureItem.lowTemp}",
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
fun FutureHourlyViewHolder(hourlyModel: HourlyModel) {
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
            text = hourlyModel.hour,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(
                id = when (hourlyModel.picPath) {
                    "cloudy" -> R.drawable.cloudy
                    "sunny" -> R.drawable.sunny
                    "wind" -> R.drawable.wind
                    "rainy" -> R.drawable.rainy
                    "storm" -> R.drawable.storm
                    else -> R.drawable.sunny
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "${hourlyModel.temp}",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}

val items = listOf(
    HourlyModel(hour = "9 pm", temp = 22, picPath = "cloudy"),
    HourlyModel(hour = "9 pm", temp = 22, picPath = "sunny"),
    HourlyModel(hour = "9 pm", temp = 22, picPath = "wind"),
    HourlyModel(hour = "9 pm", temp = 22, picPath = "rainy"),
    HourlyModel(hour = "9 pm", temp = 22, picPath = "storm"),
    HourlyModel(hour = "9 pm", temp = 22, picPath = "cloudy")
)

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