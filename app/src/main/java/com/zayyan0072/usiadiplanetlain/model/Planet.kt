package com.zayyan0072.usiadiplanetlain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Planet(
    @StringRes val namaRes: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val deskripsiRes: Int
)
