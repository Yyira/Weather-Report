package com.example.weatherreport.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherreport.network.Constant
import com.example.weatherreport.network.RetrofitInstance

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel:ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun changeTemp(newTemp:Int){
        _uiState.update { currentState ->
            currentState.copy(temp = newTemp)
        }
    }

    private val climaTempoService = RetrofitInstance.climaTempoService

    fun getCity(city:String) {

        viewModelScope.launch {
            val response = climaTempoService.getClimaTempoCity(
                appid = Constant.appid,
                city = city
            )
            if(response.isSuccessful){
                Log.i("Response : ", response.body().toString())
            }else{
                Log.i("Error : ", response.message())
            }
        }

    }

    }

