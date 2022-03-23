package com.digitals.realpokemonapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitals.realpokemonapp.data.local.PokemonDao
import com.digitals.realpokemonapp.data.local.PokemonMapper
import com.digitals.realpokemonapp.mock.PokemonUiList
import com.digitals.realpokemonapp.presentation.model.PokemonUi
import kotlinx.coroutines.launch
import kotlin.random.Random

class PokemonViewModel(private val pokemonDao: PokemonDao) : ViewModel() {

    private var _pokemonData = MutableLiveData<List<PokemonUi>>()
    val pokemonData: LiveData<List<PokemonUi>> = _pokemonData

    fun loadPokemon() = viewModelScope.launch {
        _pokemonData.value = pokemonDao.getAllPokemon().map {
            PokemonMapper().pokemonEntityToUi(it)
        }
    }

    fun addPokemon() = viewModelScope.launch {
        val rand = (0..5).random()
        pokemonDao.insertPokemon(
            PokemonMapper().pokemonUiToEntity(PokemonUiList[rand])
        )
        _pokemonData.value = pokemonDao.getAllPokemon().map {
            PokemonMapper().pokemonEntityToUi(it)
        }
    }
}