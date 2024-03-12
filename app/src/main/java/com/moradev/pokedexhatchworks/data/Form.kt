package com.moradev.pokedexhatchworks.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Form(
    @Expose @SerializedName("name")
    val name: String,
    @Expose @SerializedName("url")
    val url: String,
)
