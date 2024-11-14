package com.example.weatherreport.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherreport.network.Constant
import com.example.weatherreport.network.NetworkResponse
import com.example.weatherreport.network.RetrofitInstance
import com.example.weatherreport.network.city.CurrentCity
import com.example.weatherreport.network.city5.CurrentCity5Days

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel:ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun changeCityName(cityName: String){
        _uiState.update { currentState ->
            currentState.copy(
                cityName = cityName
            )
        }
    }

    private val climaTempoService = RetrofitInstance.climaTempoService

    private val _currentCity = MutableLiveData<NetworkResponse<CurrentCity>>()
    val currentCity:LiveData<NetworkResponse<CurrentCity>> = _currentCity
    private val _currentCity5Days = MutableLiveData<NetworkResponse<CurrentCity5Days>>()
    val currentCity5Days:LiveData<NetworkResponse<CurrentCity5Days>> = _currentCity5Days


    fun getCityWeather(city:String) {

        viewModelScope.launch {
            try {
                val response = climaTempoService.getClimaTempoCity(
                    appid = Constant.appid,
                    city = city
                )
                if(response.isSuccessful){
                    Log.i("Response : ", response.body().toString())
                    response.body()?.let {
                        _currentCity.value = NetworkResponse.Success(it)
                        //Log.i("Response : ", NetworkResponse.Success(it).toString())
                    }
                }else{
                    Log.i("Error : ", response.message())
                    _currentCity.value =NetworkResponse.Error("Failed to load data")
                }
            } catch (e:Exception){

                _currentCity.value =NetworkResponse.Error("Failed to load data")
            }

        }

    }

    fun getCity5DaysWeather(city:String) {

        viewModelScope.launch {
            try {
                val response = climaTempoService.getClimaTempoCity5Days(
                    appid = Constant.appid,
                    city = city
                )
                if(response.isSuccessful){
                    Log.i("Response : ", response.body().toString())
                    response.body()?.let {
                        _currentCity5Days.value = NetworkResponse.Success(it)
                        //Log.i("Response : ", NetworkResponse.Success(it).toString())
                    }
                }else{
                    Log.i("Error : ", response.message())
                    _currentCity5Days.value =NetworkResponse.Error("Failed to load data")
                }
            } catch (e:Exception){

                _currentCity5Days.value =NetworkResponse.Error("Failed to load data")
            }

        }

    }

    }

