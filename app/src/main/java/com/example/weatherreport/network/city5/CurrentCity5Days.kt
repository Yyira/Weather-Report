package com.example.weatherreport.network.city5

data class CurrentCity5Days(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item0>,
    val message: Int
)