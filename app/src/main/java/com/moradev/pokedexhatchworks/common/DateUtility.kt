package com.moradev.pokedexhatchworks.common

import java.util.Calendar

class DateUtility {

    companion object {
        fun getState(): Stage {
            val hour24 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val minutes = Calendar.getInstance().get(Calendar.MINUTE)

            return when {
                hour24 in 6..12 -> Stage.MORNING
                hour24 in 12..17 -> Stage.AFTERNOON
                hour24 == 18 && minutes < 30 -> Stage.AFTERNOON

                else ->
                    Stage.NIGHT
            }
        }
    }
}