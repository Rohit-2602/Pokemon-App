package com.example.pokemonapp.ui.allPokemons

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.network.Resource
import com.example.pokemonapp.ui.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPokemonsViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val pageSize = 20

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")
    private var currentPage = 2

    val searchQuery = mutableStateOf("")

    val pokemonList = mutableStateOf<List<Pokemon>>(listOf())

    init {
        getPokemonList()
    }

    fun getPokemonList(query: String = "") {
        viewModelScope.launch {
            searchQuery.value = query
            isLoading.value = true
            when (val result = repository.searchPokemons(query = query, pageSize = pageSize, page = 1)) {
                is Resource.Success -> {
                    isLoading.value = false
                    errorMessage.value = ""
                    pokemonList.value = result.data!!.pokemons
                    currentPage = 2
                }
                is Resource.Loading -> {
                    isLoading.value = true
                    errorMessage.value = ""
                }
                is Resource.Error -> {
                    isLoading.value = false
                    errorMessage.value = result.message.toString()
                }
            }
        }
    }

    fun loadMorePokemons(query: String) {
        if (currentPage == 0) {
            return
        }
        viewModelScope.launch {
            when (val result = repository.searchPokemons(query = query, pageSize = pageSize, page = currentPage)) {
                is Resource.Success -> {
                    errorMessage.value = ""
                    pokemonList.value += result.data!!.pokemons
                    currentPage = result.data.nextPage ?: 0
                }
                is Resource.Loading -> {
                    errorMessage.value = ""
                }
                is Resource.Error -> {
                    errorMessage.value = result.message.toString()
                }
            }
        }
    }

}