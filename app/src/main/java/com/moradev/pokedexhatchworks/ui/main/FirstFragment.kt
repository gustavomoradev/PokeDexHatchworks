package com.moradev.pokedexhatchworks.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.moradev.pokedexhatchworks.R
import com.moradev.pokedexhatchworks.data.Pokemon
import com.moradev.pokedexhatchworks.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val mainViewModel:MainViewModel by viewModels()

    private var offset:Int = 0
    private var limit:Int = 20
    private lateinit var mAdapter:PokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    private fun init() {

        mainViewModel.pokemonResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                savePokemonList(it.results)
                showList(mainViewModel.pokemonList)
            }
        }

        mainViewModel.getPokemonList(offset, limit)

        mAdapter = PokeAdapter {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("pokemonSelected" to it.name))
        }

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    addMorePokemon()
                }
            }
        })

    }

    private fun showList(list:List<Pokemon>) {
        if(binding.rvList.adapter == null) {
            binding.rvList.adapter = mAdapter
            mAdapter.submitList(list)
        }else {
            mAdapter.notifyItemInserted(mainViewModel.pokemonList.size)

        }

    }

    private fun addMorePokemon() {
        offset+=20
        mainViewModel.getPokemonList(offset, limit)
    }

    private fun savePokemonList(results: ArrayList<Pokemon>?) {
        if (!results.isNullOrEmpty()){
            mainViewModel.pokemonList.addAll(results)
            val mutableList = arrayListOf<Pokemon>()
            mutableList.addAll(mainViewModel.pokemonList.distinctBy { pokemon -> pokemon.name })
            mainViewModel.pokemonList.clear()
            mainViewModel.pokemonList.addAll(mutableList)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}