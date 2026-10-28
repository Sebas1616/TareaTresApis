package com.example.tareatresapis.data.repository

import com.example.tareatresapis.data.local.CharacterCacheDao
import com.example.tareatresapis.data.local.CharacterCacheEntity
import com.example.tareatresapis.data.remote.RickAndMortyApiService
import com.example.tareatresapis.data.remote.SqliteCloudApiService
import com.example.tareatresapis.data.remote.dto.SqliteQueryRequest
import com.example.tareatresapis.model.MergedCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RickRepository(
    private val cacheDao: CharacterCacheDao,
    private val rickApi: RickAndMortyApiService,
    private val sqliteCloudApi: SqliteCloudApiService
) {
    private val apiKey = "Bearer 76QYHtRPJD2bSICabrfB22msgUQmr3QcB9RZ7ZsOnB0"

    fun observarPersonajes(): Flow<List<MergedCharacter>> =
        cacheDao.observarTodos().map { entidades ->
            entidades.map { entity ->
                MergedCharacter(
                    id = entity.id,
                    name = entity.name,
                    status = entity.status,
                    species = entity.species,
                    image = entity.image,
                    pokemonNombre = null,
                    pokemonExp = null,
                    dogUrl = null
                )
            }
        }

    suspend fun descargarRickAndMorty(): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = rickApi.getCharacters()
            if (response.isSuccessful) {
                val dtos = response.body()?.results ?: emptyList()
                val entidades = dtos.map { dto ->
                    CharacterCacheEntity(
                        id = dto.id,
                        name = dto.name,
                        status = dto.status,
                        species = dto.species,
                        image = dto.image
                    )
                }

                // 1. Guardar localmente en Room
                cacheDao.insertarTodos(entidades)

                // 2. Sincronizar en SQLite Cloud
                dtos.forEach { dto ->
                    val nameSanitized = dto.name.replace("'", "''")
                    val statusSanitized = dto.status.replace("'", "''")
                    val speciesSanitized = dto.species.replace("'", "''")
                    val sql = "INSERT OR REPLACE INTO character_cache (id, name, status, species, image) VALUES (${dto.id}, '$nameSanitized', '$statusSanitized', '$speciesSanitized', '${dto.image}');"
                    sqliteCloudApi.executeQuery(apiKey, SqliteQueryRequest(sql))
                }
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizarNombrePersonaje(id: Int, nuevoNombre: String): Boolean = withContext(Dispatchers.IO) {
        try {
            // Actualizar localmente en Room
            val actual = cacheDao.obtenerPorId(id)
            if (actual != null) {
                val actualizado = actual.copy(name = nuevoNombre)
                cacheDao.insertar(actualizado)
            }

            // Actualizar en SQLite Cloud
            val nameSanitized = nuevoNombre.replace("'", "''")
            val sql = "UPDATE character_cache SET name = '$nameSanitized' WHERE id = $id;"
            sqliteCloudApi.executeQuery(apiKey, SqliteQueryRequest(sql))
            true
        } catch (e: Exception) {
            false
        }
    }
}
