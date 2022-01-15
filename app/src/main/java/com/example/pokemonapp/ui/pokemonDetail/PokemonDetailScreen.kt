package com.example.pokemonapp.ui.pokemonDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pokemonapp.R
import com.example.pokemonapp.model.PokemonDetail
import com.example.pokemonapp.model.Stat
import com.example.pokemonapp.model.Type
import com.example.pokemonapp.ui.ProgressBar
import com.example.pokemonapp.ui.RetrySection
import com.example.pokemonapp.util.Utils

@Composable
fun PokemonDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    val pokemonDetails by remember { viewModel.getPokemonDetail(id) }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            ProgressBar()
        }
        if (errorMessage.isNotEmpty()) {
            RetrySection(errorMessage = errorMessage) {
                viewModel.getPokemonDetail(id)
            }
        }
        if (!isLoading && errorMessage.isEmpty() && pokemonDetails != null) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Header(pokemon = pokemonDetails!!, navController = navController)
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = pokemonDetails!!.name,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                item {
                    TypeRow(typeList = pokemonDetails!!.types)
                }
                item {
                    MeasuresRow(pokemon = pokemonDetails!!)
                }
                item {
                    Stats(pokemonDetails!!)
                }
            }
        }
    }

}

@Composable
fun Header(pokemon: PokemonDetail, navController: NavController) {
    val painter = rememberImagePainter(data = pokemon.imageUrl)
    val backButton = rememberImagePainter(R.drawable.ic_back)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 5.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .background(color = Utils.getPokemonTypeColor(pokemon.types[0]))
    ) {
        Image(
            painter = backButton,
            contentDescription = "Back Button",
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.Black)
                .size(30.dp)
                .padding(5.dp)
                .clickable {
                    navController.navigateUp()
                }
        )
        Image(
            painter = painter,
            contentDescription = "Pokemon Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Composable
fun TypeRow(typeList: List<Type>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (type in typeList) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(20.dp))
                    .background(color = Utils.getPokemonTypeColor(type))
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = type.name,
                    style = TextStyle(color = Color.Black, fontSize = 20.sp),
                    modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
                )
            }
        }
    }
}

@Composable
fun MeasuresRow(pokemon: PokemonDetail) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${pokemon.weight / 10.0} KG",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Weight",
                style = TextStyle(color = Color.DarkGray, fontSize = 16.sp),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${pokemon.height} M",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Height",
                style = TextStyle(color = Color.DarkGray, fontSize = 16.sp),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
fun Stats(pokemon: PokemonDetail) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Base Stats",
            modifier = Modifier.padding(top = 15.dp),
            style = TextStyle(color = Color.DarkGray, fontSize = 20.sp)
        )
        for (stat in pokemon.stats) {
            StatItem(stat = stat)
        }
    }
}

@Composable
fun StatItem(stat: Stat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = Utils.parseStatToAbbr(stat = stat), modifier = Modifier.width(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .shadow(1.dp, RoundedCornerShape(10.dp))
                .background(Color.DarkGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((stat.base_stat / 300f))
                    .shadow(1.dp, RoundedCornerShape(10.dp))
                    .background(Utils.parseStatToColor(stat)),
                contentAlignment = Alignment.CenterEnd,
            ) {
                if (stat.base_stat >= 45) {
                    Text(
                        text = "${stat.base_stat}/300",
                        style = TextStyle(color = Color.Black, fontSize = 12.sp),
                        modifier = Modifier.wrapContentWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "",
                        style = TextStyle(color = Color.Black, fontSize = 12.sp),
                        modifier = Modifier.wrapContentWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (stat.base_stat < 45) {
                Text(
                    text = "${stat.base_stat}/300",
                    style = TextStyle(color = Color.White, fontSize = 12.sp),
                    modifier = Modifier.wrapContentWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
