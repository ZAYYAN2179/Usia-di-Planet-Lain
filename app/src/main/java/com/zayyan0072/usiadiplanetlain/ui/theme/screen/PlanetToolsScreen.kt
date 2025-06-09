package com.zayyan0072.usiadiplanetlain.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zayyan0072.usiadiplanetlain.R
import com.zayyan0072.usiadiplanetlain.model.MainViewModelAlatEksplorasi
import com.zayyan0072.usiadiplanetlain.model.Tools
import com.zayyan0072.usiadiplanetlain.ui.theme.UsiaDiPlanetLainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetToolsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
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
                    Text(text = stringResource(id = R.string.planet_tools_screen))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        ScreenContentTools(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
fun ScreenContentTools(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: MainViewModelAlatEksplorasi = viewModel()
    val data by viewModel.data

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize().padding(4.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(data) { ListItem(tools = it) }
    }
}

@Composable
fun ListItem(tools: Tools) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tools.gambar)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.gambar, tools.nama),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(
                    text = tools.nama,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tools.fungsi,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PlanetToolsScreenPreview() {
    UsiaDiPlanetLainTheme {
        PlanetToolsScreen(rememberNavController())
    }
}
