package com.moradev.pokedexhatchworks.ui.main

import com.moradev.pokedexhatchworks.data.Cries
import com.moradev.pokedexhatchworks.data.Pokemon
import com.moradev.pokedexhatchworks.data.PokemonDetailsResponse
import com.moradev.pokedexhatchworks.data.PokemonResponse
import com.moradev.pokedexhatchworks.net.PokeApi
import com.moradev.pokedexhatchworks.remote.MainRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class MainViewModelTest {

    @RelaxedMockK
    private lateinit var mainRepository: MainRepository

    lateinit var mainViewModel: MainViewModel

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(mainRepository)
    }


    @Test
    fun verifyPokemonDetailWasCalledAlmostOneTime() = runBlocking {
        coEvery { mainRepository.getPokemonByName("any") } returns PokemonDetailsResponse(Cries("",""), 0, 0, "", arrayListOf(),0)

        mainRepository.getPokemonByName("")

        coVerify (exactly = 0){ mainRepository.getPokemonList() }
        coVerify (exactly = 1){ mainRepository.getPokemonByName("") }
    }

    @Test
    fun verifyPokemonListWasCalled()= runBlocking {

        val mockResponse = PokemonResponse(results = arrayListOf(Pokemon("Pikachu","")))

        coEvery { mainRepository.getPokemonList() } returns mockResponse

        val response = mainRepository.getPokemonList()

        coVerify (exactly = 1){ mainRepository.getPokemonList() }
        coVerify (exactly = 0){ mainRepository.getPokemonByName("") }

        assert(response == mockResponse)
    }


    /*@org.junit.jupiter.api.Test
    fun getPokemonResponse() {
    }

    @org.junit.jupiter.api.Test
    fun getPokemonDetailsResponse() {
    }

    @org.junit.jupiter.api.Test
    fun getPokemonList() {
    }

    @org.junit.jupiter.api.Test
    fun testGetPokemonList() {
    }

    @org.junit.jupiter.api.Test
    fun getPokemonSelectedDetails() {
    }*/
}