package com.example.pokemonapp.network.response

import com.example.pokemonapp.model.PokemonDetail

data class PokemonDetailResponse(
    val success: Boolean,
    val message: String? = null,
    val pokemon: PokemonDetail?=null
)
