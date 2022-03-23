package com.digitals.realpokemonapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jsonbin.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokemonService = retrofit.create(PokemonService::class.java)
}