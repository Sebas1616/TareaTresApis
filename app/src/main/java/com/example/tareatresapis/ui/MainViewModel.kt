package com.example.tareatresapis.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.tareatresapis.data.local.AppDatabase
import com.example.tareatresapis.data.remote.RickAndMortyApiService
import com.example.tareatresapis.data.remote.RetrofitClients
import com.example.tareatresapis.data.repository.RickRepository
import com.example.tareatresapis.model.MergedCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class UiState(
    val cargando: Boolean = false,
    val characters: List<MergedCharacter> = emptyList(),
    val favoritosIds: Set<Int> = emptySet()
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "app_database"
    ).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val rickApi = retrofit.create(RickAndMortyApiService::class.java)
    private val repository = RickRepository(
        cacheDao = database.characterCacheDao(),
        rickApi = rickApi,
        sqliteCloudApi = RetrofitClients.sqliteCloudApi
    )

    private val _uiState = MutableStateFlow(UiState(cargando = true))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observarPersonajes().collect { lista ->
                _uiState.value = _uiState.value.copy(characters = lista, cargando = false)
            }
        }
        viewModelScope.launch {
            repository.descargarRickAndMorty()
        }
    }

    fun alternarFavorito(id: Int) {
        val actuales = _uiState.value.favoritosIds.toMutableSet()
        if (actuales.contains(id)) actuales.remove(id) else actuales.add(id)
        _uiState.value = _uiState.value.copy(favoritosIds = actuales)
    }

    fun cambiarNombre(id: Int, nuevoNombre: String) {
        viewModelScope.launch {
            repository.actualizarNombrePersonaje(id, nuevoNombre)
        }
    }
}