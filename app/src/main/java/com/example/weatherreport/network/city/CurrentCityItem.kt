package com.example.weatherreport.network.city

data class CurrentCityItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)