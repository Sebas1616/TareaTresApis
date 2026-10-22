package com.example.tareatresapis.data.repository

import com.example.tareatresapis.data.local.CharacterCacheDao
import com.example.tareatresapis.data.mapper.toDomain
import com.example.tareatresapis.data.remote.RetrofitClients
import com.example.tareatresapis.model.MergedCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RickRepository(
    private val cacheDao: CharacterCacheDao
) {
    private val rickApi = RetrofitClients.rickApi

    // Observa directamente de SQLite (Solo Rick & Morty)
    fun observarPersonajes(): Flow<List<MergedCharacter>> =
        cacheDao.observarTodos().map { entidades -> entidades.map { it.toDomain() } }

    suspend fun hayPersonajesGuardados(): Boolean = cacheDao.contar() > 0

    // Descarga SOLO Rick & Morty y lo guarda en Room
    suspend fun descargarRickAndMorty(): Boolean {
        return try {
            val response = rickApi.getCharacters()
            if (response.isSuccessful) {
                val dtos = response.body()?.results ?: emptyList()
                val entidades = dtos.map { dto -> 
                    com.example.tareatresapis.data.local.CharacterCacheEntity(
                        id = dto.id, name = dto.name, status = dto.status, 
                        species = dto.species, image = dto.image
                    )
                }
                cacheDao.insertarTodos(entidades)
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }
}
