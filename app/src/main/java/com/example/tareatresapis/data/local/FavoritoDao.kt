package com.example.tareatresapis.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {
    @Query("SELECT * FROM favoritos ORDER BY agregadoEn DESC")
    fun observarFavoritos(): Flow<List<FavoritoEntity>>

    @Query("SELECT id FROM favoritos")
    fun observarIdsFavoritos(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(favorito: FavoritoEntity)

    @Query("DELETE FROM favoritos WHERE id = :id")
    fun eliminarPorId(id: Int)
}
