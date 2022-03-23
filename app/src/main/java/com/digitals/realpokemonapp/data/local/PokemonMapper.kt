package com.digitals.realpokemonapp.data.local

import com.digitals.realpokemonapp.R
import com.digitals.realpokemonapp.data.remote.model.PokemonApiItem
import com.digitals.realpokemonapp.presentation.model.PokemonUi

class PokemonMapper {

    fun pokemonEntityToUi(input: PokemonEntity): PokemonUi {
        return PokemonUi(
            name = input.name,
            imageUrl = input.imageUrl,
            backgroundColor = input.backgroundColor
        )
    }

    fun pokemonUiToEntity(input: PokemonUi): PokemonEntity {
        return PokemonEntity(
            name = input.name,
            imageUrl = input.imageUrl,
            backgroundColor = input.backgroundColor
        )
    }

    fun pokemonApiToUi(input: PokemonApiItem): PokemonUi {
        return PokemonUi(
            name = input.name ?: "",
            imageUrl = input.imageUrl ?: "",
            backgroundColor = R.color.teal_700
        )
    }
}