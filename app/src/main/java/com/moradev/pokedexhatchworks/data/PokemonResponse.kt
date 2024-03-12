package com.moradev.pokedexhatchworks.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonResponse (
    @Expose @SerializedName("count")
    var count:Int = 0,

    @Expose @SerializedName("next")
    var next:String = "",

    @Expose @SerializedName("previous")
    var previous:String = "",

    @Expose @SerializedName("results")
    var results: ArrayList<Pokemon>? = null
){

}