package com.digitals.realpokemonapp.presentation.model

import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil

data class PokemonUi(
    val name: String,
    val imageUrl: String,
    @ColorRes val backgroundColor: Int
)

object PokemonDiffUtils : DiffUtil.ItemCallback<PokemonUi>() {

    override fun areItemsTheSame(oldItem: PokemonUi, newItem: PokemonUi) = oldItem == newItem

    override fun areContentsTheSame(oldItem: PokemonUi, newItem: PokemonUi) =
        oldItem.name == newItem.name
}
