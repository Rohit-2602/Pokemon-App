package com.example.pokemonapp.ui

import com.example.pokemonapp.network.ApiService
import com.example.pokemonapp.network.Resource
import com.example.pokemonapp.network.response.PokemonDetailResponse
import com.example.pokemonapp.network.response.SearchResponse
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPokemonById(pokemonId: Int): Resource<PokemonDetailResponse> {
        val result = try {
            apiService.getPokemonById(pokemonId = pokemonId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(data = result)
    }

    suspend fun searchPokemons(query: String, pageSize: Int, page: Int): Resource<SearchResponse> {
        val result = try {
            apiService.searchPokemons(name = query, pageSize = pageSize, page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(data = result)
    }

}