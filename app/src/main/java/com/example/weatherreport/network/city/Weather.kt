package com.example.weatherreport.network.city

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)