package com.example.pokemonapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokemonapp.ui.allPokemons.AllPokemonsScreen
import com.example.pokemonapp.ui.pokemonDetail.PokemonDetailScreen

@ExperimentalFoundationApi
@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.AllPokemonScreen.route
    ) {
        composable(route = Screen.AllPokemonScreen.route) {
            AllPokemonsScreen(navController = navController)
        }
        composable(
            route = Screen.PokemonDetailScreen.routeWithArgument,
            arguments = listOf(navArgument(Screen.PokemonDetailScreen.pokemonId) {
                type = NavType.IntType
            })
        ) {
            val pokemonId = it.arguments?.getInt(Screen.PokemonDetailScreen.pokemonId) ?: 1
            PokemonDetailScreen(id = pokemonId, navController = navController)
        }
    }
}