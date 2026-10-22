package com.example.tareatresapis.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareatresapis.data.local.AppDatabase
import com.example.tareatresapis.data.mapper.toFavoritoEntity
import com.example.tareatresapis.data.remote.RetrofitClients
import com.example.tareatresapis.data.repository.DogRepository
import com.example.tareatresapis.data.repository.PokemonRepository
import com.example.tareatresapis.data.repository.RickRepository
import com.example.tareatresapis.model.MergedCharacter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.obtener(application)
    private val rickRepo = RickRepository(db.characterCacheDao())
    private val pokeRepo = PokemonRepository(RetrofitClients.pokeApi)
    private val dogRepo = DogRepository(RetrofitClients.dogApi)
    private val favoritoDao = db.favoritoDao()

    private val _cargando = MutableStateFlow(true)
    private val _personajesEnriquecidos = MutableStateFlow<List<MergedCharacter>>(emptyList())

    val uiState: StateFlow<UiState> = combine(
        _personajesEnriquecidos,
        _cargando,
        favoritoDao.observarIdsFavoritos()
    ) { personajes, cargando, ids ->
        UiState(characters = personajes, cargando = cargando, favoritosIds = ids.toSet())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    init {
        viewModelScope.launch {
            if (!rickRepo.hayPersonajesGuardados()) {
                rickRepo.descargarRickAndMorty()
            }
            
            // Escucha Room y enriquece con las otras 2 APIs en tiempo real
            rickRepo.observarPersonajes().collect { listaBase ->
                _cargando.value = true
                val poke = pokeRepo.fetchPokemon()
                val dog = dogRepo.fetchRandomDog()
                
                val listaCompleta = listaBase.map { 
                    it.copy(
                        pokemonNombre = poke?.nombre, 
                        pokemonExp = poke?.experiencia, 
                        dogUrl = dog?.imageUrl
                    )
                }
                _personajesEnriquecidos.value = listaCompleta
                _cargando.value = false
            }
        }
    }

    fun alternarFavorito(character: MergedCharacter) {
        val esFavorito = character.id in uiState.value.favoritosIds
        viewModelScope.launch {
            if (esFavorito) favoritoDao.eliminarPorId(character.id)
            else favoritoDao.insertar(character.toFavoritoEntity())
        }
    }
}
