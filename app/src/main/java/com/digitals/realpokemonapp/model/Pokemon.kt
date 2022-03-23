package com.digitals.realpokemonapp.model

import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil

data class Pokemon(
    val name: String,
    val imageUrl: String,
    @ColorRes val backgroundColor: Int
)

object PokemonDiffUtils : DiffUtil.ItemCallback<Pokemon>() {

    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) =
        oldItem.name == newItem.name
}
