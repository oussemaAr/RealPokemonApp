package com.digitals.realpokemonapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digitals.realpokemonapp.data.local.PokemonDatabase
import com.digitals.realpokemonapp.data.remote.NetworkClient

class PokemonViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val pokemonDao = PokemonDatabase.instance(context).pokemonDao()
        return modelClass.constructors[0].newInstance(
            pokemonDao,
            NetworkClient().pokemonService
        ) as T
    }
}