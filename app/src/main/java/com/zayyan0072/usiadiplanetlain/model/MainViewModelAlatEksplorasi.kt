package com.zayyan0072.usiadiplanetlain.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zayyan0072.usiadiplanetlain.network.PlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModelAlatEksplorasi: ViewModel() {

    var data = mutableStateOf(emptyList<Tools>())
        private set

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = PlanetApi.service.getPlanet()
            } catch (e: Exception) {
                Log.d("MainViewModelAlatEksplorasi", "Failure: ${e.message}")
            }
        }
    }
}