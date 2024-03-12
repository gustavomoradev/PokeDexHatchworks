package com.moradev.pokedexhatchworks.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moradev.pokedexhatchworks.data.Pokemon
import com.moradev.pokedexhatchworks.data.PokemonDetailsResponse
import com.moradev.pokedexhatchworks.data.PokemonResponse
import com.moradev.pokedexhatchworks.remote.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private var mainRepo: MainRepository):ViewModel() {

    val exchangeResponse: LiveData<String>
        get() = _exchangeResponse
    private var _exchangeResponse = MutableLiveData<String>()


    val pokemonResponse: LiveData<PokemonResponse>
        get() = _pokemonResponse
    private var _pokemonResponse = MutableLiveData<PokemonResponse>()

    val pokemonDetailsResponse: LiveData<PokemonDetailsResponse>
        get() = _pokemonDetailsResponse
    private var _pokemonDetailsResponse = MutableLiveData<PokemonDetailsResponse>()


    var pokemonList = mutableListOf<Pokemon>()

    fun getPokemonList(offset:Int, limit:Int){
        viewModelScope.launch (Dispatchers.Main){
            _pokemonResponse.value = mainRepo.getPokemonList(offset, limit)
        }
    }

    fun getPokemonSelectedDetails(pokemonName: String){
        viewModelScope.launch (Dispatchers.Main){
            _pokemonDetailsResponse.value = mainRepo.getPokemonByName(pokemonName)
        }
    }

    fun getExchange(from:String="USD", to:String="CRC"){
        viewModelScope.launch(Dispatchers.Main) {
            _exchangeResponse.value = mainRepo.getExchange(from, to)
        }
    }

}