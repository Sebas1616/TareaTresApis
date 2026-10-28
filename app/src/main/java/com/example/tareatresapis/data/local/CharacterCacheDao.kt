package com.example.tareatresapis.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterCacheDao {
    @Query("SELECT * FROM character_cache")
    fun observarTodos(): Flow<List<CharacterCacheEntity>>

    @Query("SELECT * FROM character_cache WHERE id = :id LIMIT 1")
    fun obtenerPorId(id: Int): CharacterCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(personaje: CharacterCacheEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarTodos(personajes: List<CharacterCacheEntity>)
}
