package com.moradev.pokedexhatchworks.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TypeSlot(
    @Expose @SerializedName("slot")
    var slot:Int,

    @Expose @SerializedName("type")
    var type:Type,

)

class Type(name: String, url: String): Pokemon(name, url)