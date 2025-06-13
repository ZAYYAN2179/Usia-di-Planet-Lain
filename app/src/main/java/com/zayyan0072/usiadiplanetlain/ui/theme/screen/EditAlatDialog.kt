package com.zayyan0072.usiadiplanetlain.ui.theme.screen

import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.zayyan0072.usiadiplanetlain.R
import com.zayyan0072.usiadiplanetlain.model.Tools
import com.zayyan0072.usiadiplanetlain.network.PlanetApi

@Composable
fun EditAlatDialog(
    existingTool: Tools,
    bitmap: Bitmap?,
    onDismissRequest: () -> Unit,
    onImageSelected: (Bitmap) -> Unit,
    onConfirmation: (String, String) -> Unit,
) {
    val context = LocalContext.current
    var nama by remember { mutableStateOf(existingTool.nama) }
    var fungsi by remember { mutableStateOf(existingTool.fungsi) }
    var selectedBitmap by remember { mutableStateOf(bitmap) }
    var showExistingImage by remember { mutableStateOf(bitmap == null) }

    val originalNama = remember { existingTool.nama }
    val originalFungsi = remember { existingTool.fungsi }
    val originalBitmap = remember { bitmap }

    val hasChanges = remember(nama, fungsi, selectedBitmap) {
        nama != originalNama ||
                fungsi != originalFungsi ||
                selectedBitmap != originalBitmap
    }

    val cropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uri = result.uriContent
            uri?.let {
                val newBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                selectedBitmap = newBitmap
                showExistingImage = false
                onImageSelected(newBitmap)
            }
        }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                selectedBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Gambar Alat",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                } ?: run {
                    if (showExistingImage) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(PlanetApi.getToolsUrl(existingTool.gambar))
                                .crossfade(true)
                                .build(),
                            contentDescription = "Gambar Alat Saat Ini",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.loading_img),
                            error = painterResource(id = R.drawable.broken_image),
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                    }
                }

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text(text = "Nama") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )

                OutlinedTextField(
                    value = fungsi,
                    onValueChange = { fungsi = it },
                    label = { Text(text = "Fungsi") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )

                OutlinedButton(
                    onClick = {
                        val options = CropImageContractOptions(
                            null,
                            CropImageOptions(
                                imageSourceIncludeGallery = true,
                                imageSourceIncludeCamera = true,
                                fixAspectRatio = true
                            )
                        )
                        cropLauncher.launch(options)
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Ganti Gambar")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Batal")
                    }
                    OutlinedButton(
                        onClick = { onConfirmation(nama, fungsi) },
                        enabled = nama.isNotEmpty() && fungsi.isNotEmpty() && hasChanges,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}

