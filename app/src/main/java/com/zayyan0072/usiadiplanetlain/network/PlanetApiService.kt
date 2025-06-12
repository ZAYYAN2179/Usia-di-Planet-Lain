package com.zayyan0072.usiadiplanetlain.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zayyan0072.usiadiplanetlain.model.OpStatus
import com.zayyan0072.usiadiplanetlain.model.Tools
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://planetapi.bagasaldianata.my.id/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PlanetApiService {
    @GET("planets")
    suspend fun getPlanet(
        @Header("Authorization") email: String
    ): List<Tools>

    @Multipart
    @POST("planets")
    suspend fun postAlat(
        @Header("Authorization") email: String,
        @Part("nama") nama: RequestBody,
        @Part("fungsi") fungsi: RequestBody,
        @Part gambar: MultipartBody.Part
    ): OpStatus

    @FormUrlEncoded
    @POST("planets/destroy")
    suspend fun deletePlanet(
        @Header("Authorization") email: String,
        @Field("id") planetId: Int,
        @Field("_method") method: String = "DELETE"
    ): OpStatus
}

object PlanetApi {
    val service: PlanetApiService by lazy {
        retrofit.create(PlanetApiService::class.java)
    }

    fun getToolsUrl(gambar: String): String {
        return "${BASE_URL}planets/image?id=$gambar"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
