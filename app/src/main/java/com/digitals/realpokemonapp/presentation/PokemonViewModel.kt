package com.digitals.realpokemonapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitals.realpokemonapp.data.local.PokemonDao
import com.digitals.realpokemonapp.data.local.PokemonMapper
import com.digitals.realpokemonapp.data.remote.PokemonService
import com.digitals.realpokemonapp.data.remote.model.PokemonApi
import com.digitals.realpokemonapp.mock.PokemonUiList
import com.digitals.realpokemonapp.presentation.model.PokemonUi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel(
    private val pokemonDao: PokemonDao,
    private val pokemonService: PokemonService
) : ViewModel() {

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
        loadPokemon()
    }

    fun addPokemon(pokemon: PokemonUi) = viewModelScope.launch {
        pokemonDao.insertPokemon(
            PokemonMapper().pokemonUiToEntity(pokemon)
        )
        loadPokemon()
    }

    fun removePokemon(name: String) = viewModelScope.launch {
        pokemonDao.deletePokemonByName(name)
        loadPokemon()
    }

    fun loadPokemonFromNetwork() {
        pokemonService.loadPokemon().enqueue(object : Callback<PokemonApi> {
            override fun onResponse(call: Call<PokemonApi>, response: Response<PokemonApi>) {
                if (response.isSuccessful) {
                    val list = response.body()?.map {
                        PokemonMapper().pokemonApiToUi(it)
                    }
                    _pokemonData.value = list
                }else{
                    _pokemonData.value = emptyList()
                }
            }

            override fun onFailure(call: Call<PokemonApi>, t: Throwable) {
                _pokemonData.value = emptyList()
            }
        })
    }
}