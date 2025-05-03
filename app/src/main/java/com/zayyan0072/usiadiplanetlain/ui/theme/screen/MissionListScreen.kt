package com.zayyan0072.usiadiplanetlain.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.zayyan0072.usiadiplanetlain.R
import com.zayyan0072.usiadiplanetlain.ui.theme.UsiaDiPlanetLainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionListScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.kembali),
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(text = stringResource(R.string.tampilan_misi))
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFF0D47A1), titleContentColor = Color.White
            ),
        )
    }) { innerPadding ->
        MissionListScreenContent(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun MissionListScreenContent(modifier: Modifier = Modifier) {
    Text(text = "Tampilan List Misi", modifier = modifier)
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MissionListPreview() {
    UsiaDiPlanetLainTheme {
        MissionListScreenContent()
    }
}