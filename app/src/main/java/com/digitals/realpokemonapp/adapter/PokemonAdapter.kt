package com.digitals.realpokemonapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.digitals.realpokemonapp.databinding.PokemonItemBinding
import com.digitals.realpokemonapp.model.Pokemon
import com.digitals.realpokemonapp.model.PokemonDiffUtils

class PokemonAdapter : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokemonItemBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    inner class PokemonViewHolder(private val pokemonItemBinding: PokemonItemBinding) :
        RecyclerView.ViewHolder(pokemonItemBinding.root) {

        fun bind(pokemon: Pokemon) {
            pokemonItemBinding.title.text = pokemon.name
            pokemonItemBinding.pokemonImageView.load(pokemon.imageUrl)
        }
    }
}