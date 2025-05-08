package com.zayyan0072.usiadiplanetlain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mission")
data class Mission(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaPlanet: String,
    val tanggalMisi: String,
    val tipeMisi: String,
    val penemuan: String,
    val tantangan: String,
    val insightPenjelajahan: String,
    val isDeleted: Boolean = false
)

