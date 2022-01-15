package com.example.pokemonapp.navigation

sealed class Screen(val route: String) {

    object AllPokemonScreen : Screen("AllPokemon")

    object PokemonDetailScreen : Screen("PokemonDetail") {
        const val routeWithArgument: String = "PokemonDetail/{pokemonId}"
        const val pokemonId: String = "pokemonId"
    }

}
