package com.zayyan0072.usiadiplanetlain.ui.theme.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zayyan0072.usiadiplanetlain.R
import com.zayyan0072.usiadiplanetlain.model.DetailViewModel
import com.zayyan0072.usiadiplanetlain.ui.theme.UsiaDiPlanetLainTheme
import com.zayyan0072.usiadiplanetlain.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val KEY_ID_MISSION = "idMission"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMission(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var planet by remember { mutableStateOf("") }
    var tanggalMisi by remember { mutableStateOf("") }
    var tipeMisi by remember { mutableStateOf("") }
    var penemuan by remember { mutableStateOf("") }
    var tantangan by remember { mutableStateOf("") }
    var insight by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getMission(id) ?: return@LaunchedEffect
        planet = data.namaPlanet
        tanggalMisi = data.tanggalMisi
        tipeMisi = data.tipeMisi
        penemuan = data.penemuan
        tantangan = data.tantangan
        insight = data.insightPenjelajahan
    }

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
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_misi))
                    else
                        Text(text = stringResource(id = R.string.edit_misi))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF0D47A1), titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        if (planet == "" || tanggalMisi == "" || tipeMisi == "" || penemuan == "" || insight == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(
                                planet,
                                tanggalMisi,
                                tipeMisi,
                                penemuan,
                                tantangan,
                                insight
                            )
                        } else {
                            viewModel.update(
                                id,
                                planet,
                                tanggalMisi,
                                tipeMisi,
                                penemuan,
                                tantangan,
                                insight
                            )
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = Color.White
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormMission(
            selectedPlanet = planet,
            onPlanetChange = { planet = it },
            tanggalMisi = tanggalMisi,
            onTanggalMisiChange = { tanggalMisi = it },
            selectedTipeMisi = tipeMisi,
            onTipeMisiChange = { tipeMisi = it },
            penemuan = penemuan,
            onPenemuanChange = { penemuan = it },
            tantangan = tantangan,
            onTantanganChange = { tantangan = it },
            insight = insight,
            onInsightChange = { insight = it },
            modifier = Modifier.padding(padding)
        )

        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    showDialog = false
                    viewModel.softDelete(id)
                    Toast.makeText(
                        context,
                        context.getString(R.string.toast_misi_dihapus),
                        Toast.LENGTH_LONG
                    ).show()
                    navController.popBackStack()
                },
                title = stringResource(R.string.alert_title_hapus_misi),
                text = stringResource(R.string.alert_text_hapus_misi)
            )
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = Color.White
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.alert_title_hapus_misi))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    text: String,
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onConfirmation,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
            ) {
                Text(text = stringResource(R.string.tombol_hapus))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = stringResource(R.string.tombol_batal))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMission(
    selectedPlanet: String,
    onPlanetChange: (String) -> Unit,
    tanggalMisi: String,
    onTanggalMisiChange: (String) -> Unit,
    selectedTipeMisi: String,
    onTipeMisiChange: (String) -> Unit,
    penemuan: String,
    onPenemuanChange: (String) -> Unit,
    tantangan: String,
    onTantanganChange: (String) -> Unit,
    insight: String,
    onInsightChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val planetList = stringArrayResource(id = R.array.planet_list).toList()
    val tipeMisiList = listOf(
        stringResource(R.string.observasi),
        stringResource(R.string.eksperimen),
        stringResource(R.string.eksplorasi_permukaan)
    )

    var expandedPlanet by remember { mutableStateOf(false) }
    var expandedTipe by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    var datePickerVisible by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }

    if (datePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { datePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onTanggalMisiChange(dateFormatter.format(Date(millis)))
                        }
                        datePickerVisible = false
                    }
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { datePickerVisible = false }) {
                    Text(text = stringResource(R.string.tombol_batal))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedPlanet,
            onExpandedChange = { expandedPlanet = !expandedPlanet }
        ) {
            OutlinedTextField(
                value = selectedPlanet,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(R.string.planet)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPlanet)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedPlanet,
                onDismissRequest = { expandedPlanet = false }
            ) {
                planetList.forEach { planet ->
                    DropdownMenuItem(
                        text = { Text(planet) },
                        onClick = {
                            onPlanetChange(planet)
                            expandedPlanet = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = tanggalMisi,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.tanggal_misi)) },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.tanggal_misi),
                    modifier = Modifier.clickable { datePickerVisible = true }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expandedTipe,
            onExpandedChange = { expandedTipe = !expandedTipe }
        ) {
            OutlinedTextField(
                value = selectedTipeMisi,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(R.string.tipe_misi)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipe)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedTipe,
                onDismissRequest = { expandedTipe = false }
            ) {
                tipeMisiList.forEach { tipe ->
                    DropdownMenuItem(
                        text = { Text(tipe) },
                        onClick = {
                            onTipeMisiChange(tipe)
                            expandedTipe = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = penemuan,
            onValueChange = onPenemuanChange,
            label = { Text(text = stringResource(R.string.penemuan)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = tantangan,
            onValueChange = onTantanganChange,
            label = { Text(text = stringResource(R.string.tantangan)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = insight,
            onValueChange = onInsightChange,
            label = { Text(text = stringResource(R.string.insight)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            )
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailMissionPreview() {
    UsiaDiPlanetLainTheme {
        DetailMission(rememberNavController())
    }
}

