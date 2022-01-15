package com.example.pokemonapp.util

import androidx.compose.ui.graphics.Color
import com.example.pokemonapp.model.Stat
import com.example.pokemonapp.model.Type
import com.example.pokemonapp.ui.theme.*

object Utils {

    fun getPokemonTypeColor(type: Type): Color {
        return when(type.name.lowercase()) {
            "bug" -> TypeBug
            "dragon" -> TypeDragon
            "dark" -> TypeDark
            "electric" -> TypeElectric
            "fairy" -> TypeFairy
            "fighting" -> TypeFighting
            "fire" -> TypeFire
            "flying" -> TypeFlying
            "ghost" -> TypeGhost
            "grass" -> TypeGrass
            "ground" -> TypeGround
            "ice" -> TypeIce
            "normal" -> TypeNormal
            "poison" -> TypePoison
            "psychic" -> TypePsychic
            "rock" -> TypeRock
            "steel" -> TypeSteel
            "water" -> TypeWater
            else -> Color.Black
        }
    }

    fun parseStatToColor(stat: Stat): Color {
        return when(stat.name.lowercase()) {
            "hp" -> HPColor
            "attack" -> AtkColor
            "defense" -> DefColor
            "special-attack" -> SpAtkColor
            "special-defense" -> SpDefColor
            "speed" -> SpdColor
            else -> Color.White
        }
    }

    fun parseStatToAbbr(stat: Stat): String {
        return when(stat.name.lowercase()) {
            "hp" -> "HP "
            "attack" -> "ATK"
            "defense" -> "DEF"
            "special-attack" -> "SAK"
            "special-defense" -> "SDF"
            "speed" -> "SPD"
            else -> ""
        }
    }

}