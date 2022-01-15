package com.example.pokemonapp.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val weight: Int,
    val height: Int,
    val abilities: List<Ability>,
    val stats: List<Stat>,
    val types: List<Type>
)

data class Ability(
    val name: String
)

data class Stat(
    val name: String,
    val base_stat: Int
)

data class Type(
    val name: String
)
