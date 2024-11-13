package com.example.weatherreport.network

import com.example.weatherreport.network.city.CurrentCity

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimaTempoService {


    @GET("/data/2.5/weather")
    suspend fun getClimaTempoCity(
        @Query("appid") appid: String,
        @Query("q") city:String
    ): Response<CurrentCity>
}