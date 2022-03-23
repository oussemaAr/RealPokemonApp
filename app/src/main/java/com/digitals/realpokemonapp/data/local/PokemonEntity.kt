package com.digitals.realpokemonapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// pokemon_entity
@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "pokemon_name") val name: String,
    val imageUrl: String,
    val backgroundColor: Int
)