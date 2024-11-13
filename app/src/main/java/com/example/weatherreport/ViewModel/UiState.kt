package com.example.weatherreport.ViewModel

data class UiState(
    val cityName:String = "NONE",
    val pressure:Int = 1000,
    val humidity:Int = 50,
    val windSpeed: Double = 10.0,
    val feelsLike: Int = 25,
    val temp: Int = 0,


    )

enum class DataNames {
    DOM,
    SEG,
    TER,
    QUA,
    QUI,
    SEX,
    SAB
}