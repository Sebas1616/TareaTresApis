package com.example.tareatresapis.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterCacheDao {
    @Query("SELECT * FROM character_cache ORDER BY id ASC")
    fun observarTodos(): Flow<List<CharacterCacheEntity>>

    @Query("SELECT COUNT(*) FROM character_cache")
    suspend fun contar(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(characters: List<CharacterCacheEntity>)
}
