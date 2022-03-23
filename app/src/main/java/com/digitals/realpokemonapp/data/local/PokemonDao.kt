package com.digitals.realpokemonapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {

    @Insert
    suspend fun insertPokemon(pokemonEntity: PokemonEntity)

    @Insert
    suspend fun insertListPokemon(pokemonList: List<PokemonEntity>)

    @Delete
    suspend fun deletePokemon(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE id = :id LIMIT 1")
    suspend fun getPokemonById(id: Int): PokemonEntity

    @Query("DELETE FROM pokemon_table WHERE id = :id")
    suspend fun deletePokemonById(id: Int)

    @Query("DELETE FROM pokemon_table WHERE pokemon_name like :name")
    suspend fun deletePokemonByName(name: String)
}