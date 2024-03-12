package com.moradev.pokedexhatchworks.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open  class Pokemon(
    @Expose @SerializedName("name")
    var name: String,
    @Expose @SerializedName("url")
    var url: String
) {

    fun getImageURL() =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${
            url.split("/".toRegex()).dropLast(1).last()
        }.png"
}


