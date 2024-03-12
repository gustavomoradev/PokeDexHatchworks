package com.moradev.pokedexhatchworks.net

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @Headers(
        "X-RapidAPI-Key: d89e6589aamsh8f41dc59e86fc23p1b4974jsn9c1ab354e088",
        "X-RapidAPI-Host: currency-exchange.p.rapidapi.com"
    )
    @GET("/exchange")
    suspend fun getExchange(@Query("from")from:String, @Query("to")to:String):String

}