package com.zayyan0072.usiadiplanetlain.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zayyan0072.usiadiplanetlain.database.MissionDb
import com.zayyan0072.usiadiplanetlain.model.DetailViewModel
import com.zayyan0072.usiadiplanetlain.model.MainViewModel
import com.zayyan0072.usiadiplanetlain.model.RecycleBinViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = MissionDb.getInstance(context).dao
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(dao) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(dao) as T
            }

            modelClass.isAssignableFrom(RecycleBinViewModel::class.java) -> {
                RecycleBinViewModel(dao) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}