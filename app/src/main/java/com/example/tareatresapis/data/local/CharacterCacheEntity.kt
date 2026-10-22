package com.example.tareatresapis.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_cache")
data class CharacterCacheEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)
