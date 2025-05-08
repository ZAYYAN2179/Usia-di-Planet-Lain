package com.zayyan0072.usiadiplanetlain.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zayyan0072.usiadiplanetlain.database.MissionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: MissionDao) : ViewModel() {

    fun insert(
        namaPlanet: String,
        tanggalMisi: String,
        tipeMisi: String,
        penemuan: String,
        tantangan: String,
        insightPenjelajahan: String
    ) {
        val mission = Mission(
            namaPlanet = namaPlanet,
            tanggalMisi = tanggalMisi,
            tipeMisi = tipeMisi,
            penemuan = penemuan,
            tantangan = tantangan,
            insightPenjelajahan = insightPenjelajahan,
            isDeleted = false
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(mission)
        }
    }

    suspend fun getMission(id: Long): Mission? {
        return dao.getMissionById(id)
    }

    fun update(
        id: Long,
        namaPlanet: String,
        tanggalMisi: String,
        tipeMisi: String,
        penemuan: String,
        tantangan: String,
        insightPenjelajahan: String
    ) {
        val mission = Mission(
            id = id,
            namaPlanet = namaPlanet,
            tanggalMisi = tanggalMisi,
            tipeMisi = tipeMisi,
            penemuan = penemuan,
            tantangan = tantangan,
            insightPenjelajahan = insightPenjelajahan,
            isDeleted = false
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(mission)
        }
    }

    fun softDelete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.softDeleteById(id)
        }
    }
}