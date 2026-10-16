package com.example.tareatresapis.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareatresapis.data.mapper.toDomain
import com.example.tareatresapis.data.remote.RetrofitClients
import com.example.tareatresapis.data.repository.DogRepository
import com.example.tareatresapis.data.repository.PokemonRepository
import com.example.tareatresapis.data.repository.RickRepository
import com.example.tareatresapis.model.MergedCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val rickRepo: RickRepository = RickRepository(RetrofitClients.rickApi),
    private val pokeRepo: PokemonRepository = PokemonRepository(RetrofitClients.pokeApi),
    private val dogRepo: DogRepository = DogRepository(RetrofitClients.dogApi)
) : ViewModel() {

    private val _characters = MutableStateFlow<List<MergedCharacter>>(emptyList())
    val characters: StateFlow<List<MergedCharacter>> = _characters

    init {
        loadCombinedData()
    }

    fun loadCombinedData() {
        viewModelScope.launch {
            val rickDtos = rickRepo.fetchCharacters()
            val pokeDomain = pokeRepo.fetchPokemon()
            val dogDomain = dogRepo.fetchRandomDog()

            val mergedList = rickDtos.map { dto ->
                dto.toDomain(
                    pokemonNombre = pokeDomain?.nombre,
                    pokemonExp = pokeDomain?.experiencia,
                    dogUrl = dogDomain?.imageUrl
                )
            }
            _characters.value = mergedList
        }
    }
}
