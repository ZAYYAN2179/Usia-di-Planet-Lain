package com.zayyan0072.usiadiplanetlain.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zayyan0072.usiadiplanetlain.database.MissionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RecycleBinViewModel(private val dao: MissionDao) : ViewModel() {

    val deletedMissions: Flow<List<Mission>> = dao.getDeletedMissions()

    fun restoreMission(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.restoreById(id)
        }
    }

    fun deletePermanently(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun emptyRecycleBin() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.emptyRecycleBin()
        }
    }
}