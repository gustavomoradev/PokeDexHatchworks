package com.moradev.pokedexhatchworks.ui.main

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moradev.pokedexhatchworks.R
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
        holder.binding.ivPhoto.loadGlide(item.getImageURL(), holder.binding.cardLayout, holder.itemView.context)


        holder.binding.layout.setOnClickListener { onClick(item) }
    }
}

fun ImageView.loadGlide(urlImage:String, cardView:CardView, context: Context){

    Glide.with(context)
        .asBitmap()
        .load(Uri.parse(urlImage))
        .error(AppCompatResources.getDrawable(context, R.drawable.ic_block))

        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }



            override fun onResourceReady(
                resource: Bitmap,
                model: Any,
                target: Target<Bitmap>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {

                runCatching {
                    val palette = Palette.from(resource).generate()
                    val vibrant = palette.lightVibrantSwatch
                    cardView.setCardBackgroundColor(ColorStateList.valueOf(vibrant!!.rgb))
                }

                return false
            }

        })

        .into(this)
}