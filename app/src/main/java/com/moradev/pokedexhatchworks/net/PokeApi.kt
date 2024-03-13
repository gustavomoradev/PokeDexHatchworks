package com.moradev.pokedexhatchworks.net

import com.moradev.pokedexhatchworks.data.PokemonDetailsResponse
import com.moradev.pokedexhatchworks.data.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("api/v2/pokemon")
    suspend fun getPokemonList(@Query("offset")offset:Int = 0, @Query("limit")limit:Int = 20):PokemonResponse

    @GET("api/v2/pokemon/{pokemon_name}")
    suspend fun getPokemonDetail(@Path("pokemon_name") pokemonName:String): PokemonDetailsResponse

}