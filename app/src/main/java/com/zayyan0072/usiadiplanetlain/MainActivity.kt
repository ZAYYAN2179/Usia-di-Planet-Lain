package com.zayyan0072.usiadiplanetlain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zayyan0072.usiadiplanetlain.navigation.SetupNavGraph
import com.zayyan0072.usiadiplanetlain.ui.theme.UsiaDiPlanetLainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UsiaDiPlanetLainTheme {
                SetupNavGraph()
            }
        }
    }
}

