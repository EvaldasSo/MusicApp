package com.example.musicapp.data.remote

import com.example.musicapp.data.remote.dto.ProductsDto
import retrofit2.http.GET

interface DummyJsonApi {

    @GET("/products")
    suspend fun getMusicListening(): ProductsDto
}