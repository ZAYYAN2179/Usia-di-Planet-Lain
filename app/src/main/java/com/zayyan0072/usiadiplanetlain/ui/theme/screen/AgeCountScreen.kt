package com.zayyan0072.usiadiplanetlain.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zayyan0072.usiadiplanetlain.R
import com.zayyan0072.usiadiplanetlain.navigation.Screen
import com.zayyan0072.usiadiplanetlain.ui.theme.UsiaDiPlanetLainTheme
import kotlin.math.floor

data class Planet(
    val nameResId: Int, val revolutionPeriod: Double
)

// Membuat Saver untuk Planet
val PlanetSaver = listSaver<Planet, Any>(
    save = { listOf(it.nameResId, it.revolutionPeriod) },
    restore = { Planet(it[0] as Int, it[1] as Double) }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCountScreen(navController: NavHostController) {
    val planets = listOf(
        Planet(R.string.planet_mercury, 0.24),
        Planet(R.string.planet_venus, 0.62),
        Planet(R.string.planet_mars, 1.88),
        Planet(R.string.planet_jupiter, 11.86),
        Planet(R.string.planet_saturn, 29.46),
        Planet(R.string.planet_uranus, 84.01),
        Planet(R.string.planet_neptune, 164.79)
    )

    var statusDropdown by rememberSaveable { mutableStateOf(false) }
    var planetTerpilih by rememberSaveable(stateSaver = PlanetSaver) { mutableStateOf(planets[0]) }
    var planetDihitung by rememberSaveable(stateSaver = PlanetSaver) { mutableStateOf(planets[0]) }
    var inputUsia by rememberSaveable { mutableStateOf("") }
    var usiaDiPlanetTerpilih by rememberSaveable { mutableStateOf<Double?>(null) }
    var statusHitung by rememberSaveable { mutableStateOf(false) }
    var pesanKesalahan by rememberSaveable { mutableStateOf<String?>(null) }
    val konteks = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.kembali),
                    tint = Color.White
                )
            }
        }, title = {
            Text(text = stringResource(R.string.tampilan_hitung_usia))
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF0D47A1), titleContentColor = Color.White
        ), actions = {
            IconButton(onClick = {
                navController.navigate(Screen.About.route)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(R.string.tentang_aplikasi),
                    tint = Color.White
                )
            }
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = inputUsia,
                onValueChange = {
                    inputUsia = it
                    pesanKesalahan = null
                },
                label = { Text(stringResource(R.string.input_usia)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = pesanKesalahan != null,
                supportingText = {
                    pesanKesalahan?.let {
                        Text(
                            text = it, color = Color.Red
                        )
                    }
                })

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = statusDropdown,
                onExpandedChange = { statusDropdown = !statusDropdown },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = stringResource(planetTerpilih.nameResId),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.pilih_planet)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(expanded = statusDropdown,
                    onDismissRequest = { statusDropdown = false }) {
                    planets.forEach { planet ->
                        DropdownMenuItem(text = { Text(stringResource(planet.nameResId)) },
                            onClick = {
                                planetTerpilih = planet
                                statusDropdown = false
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (inputUsia.isBlank()) {
                        pesanKesalahan = konteks.getString(R.string.error_kosong)
                        return@Button
                    }

                    val usiaBumi = inputUsia.toDoubleOrNull()
                    if (usiaBumi == null) {
                        pesanKesalahan = konteks.getString(R.string.error_invalid)
                        return@Button
                    }

                    if (usiaBumi <= 0) {
                        pesanKesalahan = konteks.getString(R.string.error_negatif)
                        return@Button
                    }

                    pesanKesalahan = null
                    planetDihitung = planetTerpilih
                    usiaDiPlanetTerpilih = usiaBumi / planetTerpilih.revolutionPeriod
                    statusHitung = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6))
            ) {
                Text(stringResource(R.string.tombol_hitung_usia))
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (statusHitung && usiaDiPlanetTerpilih != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.hasil_perhitungan),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(
                                R.string.usia_hasil, stringResource(planetDihitung.nameResId)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val totalYears = floor(usiaDiPlanetTerpilih!!).toInt()
                        val totalMonths = ((usiaDiPlanetTerpilih!! - totalYears) * 12).toInt()
                        val totalDays =
                            ((usiaDiPlanetTerpilih!! - totalYears - totalMonths / 12.0) * 365.25).toInt()
                        // Menampilkan hasil dalam format tahun, bulan, atau hari sesuai kondisi
                        when {
                            totalYears > 0 -> {
                                Text(
                                    text = "$totalYears ${stringResource(R.string.tahun)}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0D47A1),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }

                            totalMonths > 0 -> {
                                Text(
                                    text = "$totalMonths ${stringResource(R.string.bulan)}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0D47A1),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }

                            totalDays > 0 -> {
                                Text(
                                    text = "$totalDays ${stringResource(R.string.hari)}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0D47A1),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        // Menampilkan ringkasan konversi waktu planet
                        val planetSummary = when (planetDihitung.nameResId) {
                            R.string.planet_mercury -> stringResource(R.string.ringkasan_merkurius)
                            R.string.planet_venus -> stringResource(R.string.ringkasan_venus)
                            R.string.planet_mars -> stringResource(R.string.ringkasan_mars)
                            R.string.planet_jupiter -> stringResource(R.string.ringkasan_jupiter)
                            R.string.planet_saturn -> stringResource(R.string.ringkasan_saturnus)
                            R.string.planet_uranus -> stringResource(R.string.ringkasan_uranus)
                            R.string.planet_neptune -> stringResource(R.string.ringkasan_neptunus)
                            else -> ""
                        }
                        // Menampilkan ringkasan waktu planet
                        Text(
                            text = planetSummary,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(
                                R.string.konversi_tahun,
                                stringResource(planetDihitung.nameResId),
                                planetDihitung.revolutionPeriod
                            ),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AgeCountScreenPreview() {
    UsiaDiPlanetLainTheme {
        AgeCountScreen(rememberNavController())
    }
}