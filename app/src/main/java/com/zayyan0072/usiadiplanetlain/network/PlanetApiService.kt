package com.zayyan0072.usiadiplanetlain.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://planetapi.bagasaldianata.my.id/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PlanetApiService {
    @GET("planets/")
    suspend fun getPlanet(): String
}

object PlanetApi {
    val service: PlanetApiService by lazy {
        retrofit.create(PlanetApiService::class.java)
    }
}