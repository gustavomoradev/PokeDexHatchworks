package com.moradev.pokedexhatchworks.ui.main

import android.app.ProgressDialog
import android.content.Context
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

     var offset:Int = 0
     var limit:Int = 20
    private lateinit var mAdapter:PokeAdapter
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() {
        setUpView()
        setUpEvents()
        setUpObservables()
        callPokemonService()
    }



    private fun setUpView() {
        mAdapter = PokeAdapter {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("pokemonSelected" to it.name))
        }
    }

    private fun setUpEvents() {
        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    addMorePokemon()
                }
            }
        })
    }

    private fun setUpObservables() {
        mainViewModel.pokemonResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                progressDialog?.dismiss()
                savePokemonList(it.results)
                showList(mainViewModel.pokemonList)
            }
        }
    }

    private fun callPokemonService() {
        requireContext().showLoading{ progressDialog = it }
        mainViewModel.getPokemonList(offset, limit)
    }



    private fun showList(list:List<Pokemon>) {
        if(binding.rvList.adapter == null) {
            binding.rvList.adapter = mAdapter
            mAdapter.submitList(list)
        }else {
            mAdapter.notifyItemInserted(mainViewModel.pokemonList.size)
        }
    }

     fun addMorePokemon() {
        offset+=20
        mainViewModel.getPokemonList(offset, limit)
    }

    fun savePokemonList(results: ArrayList<Pokemon>?) {
        if (!results.isNullOrEmpty()){
            mainViewModel.pokemonList.addAll(results)
            val mutableList = arrayListOf<Pokemon>()
            mutableList.addAll(mainViewModel.pokemonList.distinctBy { pokemon -> pokemon.name })
            mainViewModel.pokemonList.clear()
            mainViewModel.pokemonList.addAll(mutableList)
        }
    }


}

fun Context.showLoading(msg:String = "Loading, please wait...", dismiss:(ProgressDialog) ->Unit ){
    val progress = ProgressDialog.show(this, "", msg)
    dismiss(progress)
}