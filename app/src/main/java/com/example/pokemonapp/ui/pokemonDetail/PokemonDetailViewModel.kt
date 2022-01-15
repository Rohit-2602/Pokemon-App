package com.example.pokemonapp.ui.pokemonDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.model.PokemonDetail
import com.example.pokemonapp.network.Resource
import com.example.pokemonapp.ui.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    private val pokemon = mutableStateOf<PokemonDetail?>(null)

    fun getPokemonDetail(id: Int): MutableState<PokemonDetail?> {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getPokemonById(pokemonId = id)) {
                is Resource.Success -> {
                    isLoading.value = false
                    errorMessage.value = ""
                    pokemon.value = result.data!!.pokemon
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
        return pokemon
    }



}