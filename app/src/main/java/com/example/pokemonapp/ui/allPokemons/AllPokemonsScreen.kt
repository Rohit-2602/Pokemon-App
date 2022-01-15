package com.example.pokemonapp.ui.allPokemons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.ui.ProgressBar
import com.example.pokemonapp.ui.RetrySection
import com.example.pokemonapp.util.Utils

@ExperimentalFoundationApi
@Composable
fun AllPokemonsScreen(
    navController: NavController,
    viewModel: AllPokemonsViewModel = hiltViewModel()
) {

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isLoading) {
            ProgressBar()
        }
        if (errorMessage.isNotEmpty()) {
            RetrySection(errorMessage = errorMessage) {
                viewModel.getPokemonList(viewModel.searchQuery.value)
            }
        }
        if (errorMessage.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar(hint = "Search...") {
                    viewModel.getPokemonList(it)
                }
                if (!isLoading) {
                    PokemonList(navController = navController)
                }
            }
        }

    }

}

@Composable
fun SearchBar(
    viewModel: AllPokemonsViewModel = hiltViewModel(),
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var isHintDisplayed by remember { mutableStateOf(hint != "") }
    Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp)) {
        BasicTextField(
            value = viewModel.searchQuery.value,
            onValueChange = {
                if (it != "") {
                    isHintDisplayed = false
                }
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(color = Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = viewModel.searchQuery.value.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PokemonList(navController: NavController, viewModel: AllPokemonsViewModel = hiltViewModel()) {
    val pokemonList by remember { viewModel.pokemonList }

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(end = 10.dp, top = 10.dp)
    ) {
        itemsIndexed(pokemonList) { index, pokemon ->
            if (index >= pokemonList.size - 1 && !viewModel.isLoading.value && viewModel.errorMessage.value.isEmpty()) {
                viewModel.loadMorePokemons(query = viewModel.searchQuery.value)
            }
            PokemonCardItem(pokemon = pokemon, navController = navController)
        }
    }
}

@Composable
fun PokemonCardItem(
    pokemon: Pokemon,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(start = 10.dp, bottom = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(
                color = Utils.getPokemonTypeColor(pokemon.types[0])
            )
            .clickable {
                navController.navigate("${Screen.PokemonDetailScreen.route}/${pokemon.id}")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter =
            rememberImagePainter(data = pokemon.imageUrl)
        Image(
            painter = painter,
            contentDescription = "Pokemon Image",
            modifier = Modifier
                .size(150.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(top = 10.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = pokemon.name,
            style = TextStyle(color = Color.Black, fontSize = 18.sp)
        )
    }
}
