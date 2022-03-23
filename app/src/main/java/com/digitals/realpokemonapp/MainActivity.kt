package com.digitals.realpokemonapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.digitals.realpokemonapp.adapter.PokemonAdapter
import com.digitals.realpokemonapp.databinding.ActivityMainBinding
import com.digitals.realpokemonapp.mock.pokemonList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonAdapter = PokemonAdapter()
        binding.pokemonRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = pokemonAdapter
        }
        pokemonAdapter.submitList(pokemonList)
    }
}