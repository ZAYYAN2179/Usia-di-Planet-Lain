package com.zayyan0072.usiadiplanetlain.model

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val data = listOf(
        Mission(
            1,
            "Mars",
            "04 Mei 2025",
            "Observasi",
            "Terdapat formasi batuan unik di kawah selatan.",
            "Suhu sangat rendah dan badai debu mengganggu visibilitas.",
            "Gravitasi rendah memengaruhi pergerakan drone eksplorasi."
        ),
        Mission(
            2,
            "Venus",
            "10 Mei 2025",
            "Pemantauan Atmosfer",
            "Awan asam sulfat sangat tebal dan reflektif.",
            "Tekanan atmosfer tinggi membuat alat ukur cepat rusak.",
            "Kondisi atmosfer ekstrem tidak cocok untuk kehidupan manusia."
        ),
        Mission(
            3,
            "Saturnus",
            "29 Mei 2025",
            "Eksperimen",
            "Cincin Saturnus mengandung partikel es dan batu kecil.",
            "Navigasi sulit di sekitar cincin karena kepadatan partikel.",
            "Struktur cincin memberikan informasi tentang pembentukan planet. "
        )
    )

    fun getMission(id: Long): Mission? {
        return data.find { it.id == id }
    }
}