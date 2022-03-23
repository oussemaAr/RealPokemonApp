package com.digitals.realpokemonapp.data.remote

import com.digitals.realpokemonapp.data.remote.model.PokemonApi
import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {

    @GET("b/62388171a703bb6749311ec7")
    fun loadPokemon(): Call<PokemonApi>
}