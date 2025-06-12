package com.zayyan0072.usiadiplanetlain.model

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zayyan0072.usiadiplanetlain.network.ApiStatus
import com.zayyan0072.usiadiplanetlain.network.PlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class MainViewModelAlatEksplorasi: ViewModel() {

    var data = mutableStateOf(emptyList<Tools>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    var deleteStatus = MutableStateFlow<ApiStatus?>(null)
        private set

    fun retrieveData(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                val result = PlanetApi.service.getPlanet(email)
                data.value = result
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModelAlatEksplorasi", "Failure: ${e.message}")
                data.value = emptyList() // Set data kosong saat error
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(email: String, nama: String, fungsi: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("UploadDebug", "Email: $email, Nama: $nama, Fungsi: $fungsi")
                val result = PlanetApi.service.postAlat(
                    email,
                    nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                    fungsi.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultiPartBody()
                )

                if (result.status == "success")
                    retrieveData(email)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteData(email: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteStatus.value = ApiStatus.LOADING
                Log.d("DeleteDebug", "Email: $email, ID: $id")

                // Sekarang menggunakan DELETE murni ke endpoint /planets/destroy
                val result = PlanetApi.service.deletePlanet("Bearer $email", id)

                if (result.status == "success") {
                    deleteStatus.value = ApiStatus.SUCCESS
                    retrieveData(email)
                } else {
                    deleteStatus.value = ApiStatus.FAILED
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Delete Failure: ${e.message}")
                deleteStatus.value = ApiStatus.FAILED
                errorMessage.value = "Error menghapus data: ${e.message}"
            }
        }
    }

    private fun Bitmap.toMultiPartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "gambar", "image.jpg", requestBody)
    }

    fun clearMessage() {
        errorMessage.value = null
        deleteStatus.value = null
    }

    fun setStatusFailed() {
        status.value = ApiStatus.FAILED
    }
}