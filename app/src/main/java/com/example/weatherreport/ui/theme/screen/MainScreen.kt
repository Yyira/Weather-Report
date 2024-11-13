package com.example.weatherreport.ui.theme.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherreport.R
import com.example.weatherreport.ViewModel.AppViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    viewModel.getCity("Fortaleza")
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(
                modifier = Modifier.height(100.dp)
            )
            Text(
                text = uiState.value.temp.toString()
            )
            Text(
                text = "${uiState.value.temp}°", fontSize = 100.sp
            )
            Spacer(modifier = Modifier.height(35.dp))

            LazyRow {
                val days = listOf(1,2,3,4,5)
                items(5) {

                    ClimaCardDay(
                        temp = uiState.value.temp
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {

                    ClimaCardInfo(
                        topic = "Umidade",
                        description = "${uiState.value.humidity}%",
                        image = R.drawable.umidade,
                        fill = 0.5F
                    )

                    ClimaCardInfo(
                        topic = "Pressão",
                        description = "${uiState.value.pressure}",
                        image = R.drawable.seta_para_baixo
                    )
                }
                Row {

                    ClimaCardInfo(
                        topic = "Sensação termica",
                        description = "${uiState.value.feelsLike}°",
                        image = R.drawable.alta_temperatura,
                        fill = 0.5F
                    )

                    ClimaCardInfo(
                        topic = "Velocidade do vento",
                        description = "${uiState.value.windSpeed}",
                        image = R.drawable.vento
                    )
                }
            }

        }
    }
}

@Composable
fun ClimaCardInfo(
    topic: String,
    description: String,
    image: Int,
    fill: Float = 1F
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
                text = topic,
                fontSize = 20.sp
            )
            Text(
                text = description
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxHeight()

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
    temp:Int
) {
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
                painter = painterResource(R.drawable.nuvem),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(text = "SEG")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}