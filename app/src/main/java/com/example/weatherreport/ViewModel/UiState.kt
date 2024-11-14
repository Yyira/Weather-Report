package com.example.weatherreport.ViewModel

data class UiState(
    val cityName:String = "",


    val day1: dayOfWeek? = null,
    val day2: dayOfWeek? = null,
    val day3: dayOfWeek? = null,
    val day4: dayOfWeek? = null,
    val day5: dayOfWeek? = null,



    )

data class dayOfWeek(
    val icon:String,
    val temp: Int,
    val day:String
)