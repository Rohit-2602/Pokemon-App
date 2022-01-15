package com.example.pokemonapp.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<Type>
)