package com.example.pokemonapp.network.response

import com.example.pokemonapp.model.Pokemon

data class SearchResponse(
    val success: Boolean,
    val message: String? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val pokemons: List<Pokemon> = emptyList()
)
