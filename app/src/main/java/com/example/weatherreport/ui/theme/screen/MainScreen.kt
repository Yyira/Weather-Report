package com.example.weatherreport.ui.theme.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherreport.R
import com.example.weatherreport.ViewModel.AppViewModel
import com.example.weatherreport.network.NetworkResponse
import com.example.weatherreport.network.city.CurrentCity
import com.example.weatherreport.network.city5.Item0
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(),
) {


    val uiState = viewModel.uiState.collectAsState()
    val currentCityResult = viewModel.currentCity.observeAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp, top = 20.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    label = { Text(text = "Digite a cidade") },
                    value = uiState.value.cityName,
                    onValueChange = { viewModel.changeCityName(it) },
                )
                Icon(imageVector = Icons.Default.Send,
                    contentDescription = "Send city name",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable(enabled = true) {
                            viewModel.getCityWeather(uiState.value.cityName)
                        })
            }

            when (val result = currentCityResult.value) {
                is NetworkResponse.Error -> {
                    Column {
                        Text(text = result.message)
                    }
                }

                NetworkResponse.Loading -> {

                    CircularProgressIndicator()

                }

                is NetworkResponse.Success -> {
                    MainScreenSuccess(
                        data = result.data
                    )
                }

                null -> Text(text = "Digite uma cidade")
            }
        }
    }
}


@Composable
fun MainScreenSuccess(
    data: CurrentCity, viewModel: AppViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsState()
    viewModel.getCity5DaysWeather("Fortaleza")
    val currentCity5DaysResult = viewModel.currentCity5Days.observeAsState()
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(
            modifier = Modifier.height(50.dp)
        )
        Text(

            text = data.name
        )
        Text(
            text = "${(data.main.temp - 273).toInt()}°", fontSize = 100.sp
        )
        Spacer(modifier = Modifier.height(35.dp))


        when (val result = currentCity5DaysResult.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                ClimaCardRow(
                    list = result.data.list
                )
            }

            null -> CircularProgressIndicator()
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {

                ClimaCardInfo(
                    topic = "Umidade",
                    description = "${data.main.humidity}%",
                    image = R.drawable.umidade,
                    fill = 0.5F
                )

                ClimaCardInfo(
                    topic = "Pressão",
                    description = "${data.main.pressure}",
                    image = R.drawable.seta_para_baixo
                )
            }
            Row {

                ClimaCardInfo(
                    topic = "Sensação termica",
                    description = "${(data.main.feels_like - 273).toInt()}°",
                    image = R.drawable.alta_temperatura,
                    fill = 0.5F
                )

                ClimaCardInfo(
                    topic = "Velocidade do vento",
                    description = "${data.wind.speed}",
                    image = R.drawable.vento
                )
            }
            Row {

                ClimaCardInfo(
                    topic = "Temperatura máxima",
                    description = "${(data.main.temp_max-273).toInt()}°",
                    image = R.drawable.aumento_de_temperatura,
                    fill = 0.5F
                )

                ClimaCardInfo(
                    topic = "Temperatura mínima",
                    description = "${(data.main.temp_min-273).toInt()}",
                    image = R.drawable.baixa_de_temperatura__1_
                )
            }
        }
    }
}

@Composable
fun ClimaCardRow(
    list: List<Item0>,
) {
    val newList = listOf(list[0], list[1], list[2], list[3], list[4])
    val dataTime = java.time.LocalDate.now()
    val dayOfWeek = java.time.DayOfWeek.from(dataTime)
    LazyRow {

        items(newList.size) { index ->

            ClimaCardDay(
                temp = newList[index].main.temp.toInt() - 273,
                image = IconsId.map.get(newList[index].weather[0].icon),
                dayOfWeek = dayOfWeek.value + index
            )
        }
    }
}

@Composable
fun ClimaCardInfo(
    topic: String, description: String, image: Int, fill: Float = 1F
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(fill)
            .height(150.dp)

    ) {

        Column(
            modifier = Modifier.padding(8.dp)

        ) {
            Text(
                text = topic, fontSize = 20.sp
            )
            Text(
                text = description
            )
            Row(
                verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxHeight()

            ) {
                Spacer(
                    modifier = Modifier.weight(1F)
                )
                Image(
                    painter = painterResource(image),
                    contentDescription = topic,
                    modifier = Modifier.size(55.dp)

                )
            }

        }
    }
}

@Composable
fun ClimaCardDay(
    temp: Int, @DrawableRes image: Int?, dayOfWeek: Int
) {
    var newDayOfWeek = dayOfWeek
    Card(
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "$temp°"
            )
            Image(
                painter = painterResource(image ?: R.drawable.nuvem),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            if (dayOfWeek > 7) newDayOfWeek -= 7
            val text = DayWeek.map.get(newDayOfWeek)
            Text(text = text.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}