package com.moradev.pokedexhatchworks.remote

import com.moradev.pokedexhatchworks.data.PokemonDetailsResponse
import com.moradev.pokedexhatchworks.data.PokemonResponse
import com.moradev.pokedexhatchworks.net.PokeApi
import javax.inject.Inject


class MainRepository
@Inject constructor(private var api:PokeApi){

    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): PokemonResponse {
        return api.getPokemonList(offset, limit)
    }

    suspend fun getPokemonByName(pokemonName:String):PokemonDetailsResponse{
        return api.getPokemonDetail(pokemonName)
    }
}