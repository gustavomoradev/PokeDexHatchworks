package com.moradev.pokedexhatchworks.ui.main

import com.moradev.pokedexhatchworks.data.Cries
import com.moradev.pokedexhatchworks.data.Pokemon
import com.moradev.pokedexhatchworks.data.PokemonDetailsResponse
import com.moradev.pokedexhatchworks.data.PokemonResponse
import com.moradev.pokedexhatchworks.remote.MainRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class MainRepositoryTest {

    @RelaxedMockK
    private lateinit var mainRepository: MainRepository
    @RelaxedMockK
    private lateinit var pokemonResponseMock: PokemonResponse

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
    fun verifyPokemonListReturnSomething()= runBlocking{
        coEvery { mainRepository.getPokemonList()  } returns pokemonResponseMock
        mainRepository.getPokemonList()
        coVerify (exactly = 1){ mainRepository.getPokemonList() }

        assert(mainRepository.getPokemonList().results.isNullOrEmpty())// return something but is empty
        assert(mainRepository.getPokemonList().results?.first() != null) // this failed because mock generate results on blank or  null
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

    @Test
    fun verifyExchange()= runBlocking {
        val mockExchange = "520"

        coEvery { mainRepository.getExchange("","") }returns mockExchange

        val response = mainRepository.getExchange("","")

        coVerify (exactly = 1){ mainRepository.getExchange("","") }
        coVerify (exactly = 0){ mainRepository.getPokemonList() }
        coVerify (exactly = 0){ mainRepository.getPokemonByName("") }


        assert(response== mockExchange)
        assert(response == "520")
    }


}