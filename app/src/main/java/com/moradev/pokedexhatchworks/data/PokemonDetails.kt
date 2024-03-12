package com.moradev.pokedexhatchworks.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonDetailsResponse (
//        val abilities: List<Ability>,
//        @JsonProperty("base_experience")
//        val baseExperience: Long,
    @Expose @SerializedName("cries") //sonido
        val cries: Cries,
    /*@Expose @SerializedName("forms") //forma
        val forms: List<Form>,*/
//        @JsonProperty("game_indices")
//        val gameIndices: List<Index>,
    @Expose @SerializedName("height")//altura
        val height: Long,
//        @JsonProperty("held_items")
//        val heldItems: List<HeldItem>,
    @Expose @SerializedName("id")
        val id: Long,
//        @JsonProperty("is_default")
//        val isDefault: Boolean,
//        @JsonProperty("location_area_encounters")
//        val locationAreaEncounters: String,
//        val moves: List<Mfe>,
    @Expose @SerializedName("name")
        val name: String,
//        val order: Long,
//        @JsonProperty("past_abilities")
//        val pastAbilities: List<Any?>,
//        @JsonProperty("past_types")
//        val pastTypes: List<Any?>,
//        val species: Species,
//        val sprites: Sprites,
//        val stats: List<Stat>,
        @Expose @SerializedName("types")//tipos
        val types: ArrayList<TypeSlot>,

        @Expose @SerializedName("weight")//peso
        val weight: Long
)