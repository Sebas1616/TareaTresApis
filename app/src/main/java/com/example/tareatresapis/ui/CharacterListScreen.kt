package com.example.tareatresapis.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tareatresapis.model.MergedCharacter

@Composable
fun CharacterListScreen(viewModel: MainViewModel) {
    val characters by viewModel.characters.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(characters) { character ->
                CharacterCard(character = character)
            }
        }
    }
}

@Composable
fun CharacterCard(character: MergedCharacter) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(90.dp).clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = character.name, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = character.species, color = Color.Gray, fontSize = 12.sp)

            character.pokemonNombre?.let { nombre ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Poke: $nombre (${character.pokemonExp} EXP)",
                    fontSize = 11.sp,
                    color = Color(0xFFFFCC00)
                )
            }

            character.dogUrl?.let { url ->
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Dog API: ", fontSize = 10.sp, color = Color.LightGray)
                    AsyncImage(
                        model = url,
                        contentDescription = "Dog",
                        modifier = Modifier.size(28.dp).clip(CircleShape)
                    )
                }
            }
        }
    }
}
