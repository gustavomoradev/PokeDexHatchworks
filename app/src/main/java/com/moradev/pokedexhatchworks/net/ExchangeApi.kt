package com.moradev.pokedexhatchworks.net

import com.moradev.pokedexhatchworks.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @Headers(
        "X-RapidAPI-Key: ${BuildConfig.API_KEY}",
        "X-RapidAPI-Host: ${BuildConfig.API_HOST}"
    )
    @GET("/exchange")
    suspend fun getExchange(@Query("from") from: String, @Query("to") to: String): String

}