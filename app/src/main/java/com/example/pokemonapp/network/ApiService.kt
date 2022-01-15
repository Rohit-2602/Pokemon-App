package com.example.pokemonapp.network

import com.example.pokemonapp.network.response.PokemonDetailResponse
import com.example.pokemonapp.network.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://poki-dex.herokuapp.com/"
    }

    @GET("pokemons/search")
    suspend fun searchPokemons(
        @Query("name") name: String,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): SearchResponse

    @GET("pokemons/detail")
    suspend fun getPokemonById(
        @Query("id") pokemonId: Int
    ): PokemonDetailResponse

}