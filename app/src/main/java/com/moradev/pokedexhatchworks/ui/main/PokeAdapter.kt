package com.moradev.pokedexhatchworks.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.moradev.pokedexhatchworks.data.Pokemon
import com.moradev.pokedexhatchworks.databinding.ItemPokeListBinding

class PokeAdapter(var onClick:(Pokemon)->Unit):ListAdapter<Pokemon, PokeAdapter.ViewHolder>(diffCallBack) {

    inner class ViewHolder internal constructor(val binding:ItemPokeListBinding):RecyclerView.ViewHolder(binding.root)

    companion object{
        private val  diffCallBack:DiffUtil.ItemCallback<Pokemon> = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name && oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name && oldItem.url == newItem.url
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeAdapter.ViewHolder {
        return ViewHolder(ItemPokeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PokeAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.txvName.text = item.name.replaceFirstChar { item.name.substring(0,1).uppercase() }
        holder.binding.ivPhoto.load(item.getImageURL()){
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        holder.binding.layout.setOnClickListener { onClick(item) }
    }
}