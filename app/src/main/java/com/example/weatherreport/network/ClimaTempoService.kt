package com.example.weatherreport.network

import com.example.weatherreport.network.city.CurrentCity
import com.example.weatherreport.network.city5.CurrentCity5Days

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimaTempoService {


    @GET("/data/2.5/weather")
    suspend fun getClimaTempoCity(
        @Query("appid") appid: String,
        @Query("q") city:String
    ): Response<CurrentCity>
    @GET("/data/2.5/forecast")

    suspend fun getClimaTempoCity5Days(
        @Query("appid") appid: String,
        @Query("q") city:String
    ): Response<CurrentCity5Days>
}