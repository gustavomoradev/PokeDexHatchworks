package com.moradev.pokedexhatchworks.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moradev.pokedexhatchworks.data.Pokemon
import com.moradev.pokedexhatchworks.remote.MainRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @RelaxedMockK
    private lateinit var mainRepository: MainRepository
    @RelaxedMockK
    private lateinit var pokemonList:ArrayList<Pokemon>

    private lateinit var mainViewModel:MainViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(mainRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel has pokemon`() = runTest {

        coEvery { mainViewModel.getPokemonList(0,20) }
        mainViewModel.mockPokemonResponseTest(pokemonList)
        mainViewModel.pokemonResponse.value?.results?.isNotEmpty()?.let { assert(it) }

    }

    @Test
    fun `when exchange Has Not Any String at Response`() = runTest {
        coEvery { mainViewModel.getExchange() }

        assert(mainViewModel.exchangeResponse.value.isNullOrEmpty())

    }

    @Test
    fun `when exchange has some String at response`() = runTest {
        coEvery { mainViewModel.getExchange() }

        mainViewModel.mockExchangeResponseTest("anyValue")

        mainViewModel.exchangeResponse.value?.isNotEmpty()?.let { assert(it) }

    }



}