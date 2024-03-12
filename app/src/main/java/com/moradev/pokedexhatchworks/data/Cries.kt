package com.moradev.pokedexhatchworks.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cries(
    @Expose @SerializedName("latest")
    val latest: String,
    @Expose @SerializedName("legacy")
    val legacy: String)