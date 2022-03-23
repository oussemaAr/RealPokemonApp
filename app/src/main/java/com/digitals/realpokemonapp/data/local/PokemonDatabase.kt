package com.digitals.realpokemonapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonEntity::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {

        private var INSTANCE: PokemonDatabase? = null

        fun instance(context: Context): PokemonDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context,
                PokemonDatabase::class.java,
                "pokemon_db"
            ).build()
        }
    }
}