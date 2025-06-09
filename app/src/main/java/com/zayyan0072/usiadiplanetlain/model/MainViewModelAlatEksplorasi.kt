package com.zayyan0072.usiadiplanetlain.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zayyan0072.usiadiplanetlain.network.PlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModelAlatEksplorasi: ViewModel() {

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = PlanetApi.service.getPlanet()
                Log.d("MainViewModelAlatEksplorasi", "Success: $result")
            } catch (e: Exception) {
                Log.d("MainViewModelAlatEksplorasi", "Failure: ${e.message}")
            }
        }
    }
}