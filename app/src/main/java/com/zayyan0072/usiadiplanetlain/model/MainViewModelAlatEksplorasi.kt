package com.zayyan0072.usiadiplanetlain.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zayyan0072.usiadiplanetlain.network.ApiStatus
import com.zayyan0072.usiadiplanetlain.network.PlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModelAlatEksplorasi: ViewModel() {

    var data = mutableStateOf(emptyList<Tools>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = PlanetApi.service.getPlanet()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModelAlatEksplorasi", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}