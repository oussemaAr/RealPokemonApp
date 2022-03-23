package com.digitals.realpokemonapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.digitals.realpokemonapp.databinding.ActivityMainBinding
import com.digitals.realpokemonapp.mock.PokemonUiList
import com.digitals.realpokemonapp.presentation.adapter.PokemonAdapter
import com.digitals.realpokemonapp.presentation.model.PokemonUi
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pokemonAdapter = PokemonAdapter()

    private val viewModel: PokemonViewModel by viewModels {
        PokemonViewModelFactory(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pokemonRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = pokemonAdapter
            initTouchListener(pokemonAdapter)
        }

        viewModel.loadPokemon()

        viewModel.pokemonData.observe(this) { listPokemon ->
            Log.e("TAG", "onCreate: ${listPokemon.size}")
            pokemonAdapter.submitList(listPokemon)
            binding.pokemonSwipeToRefresh.isRefreshing = false
        }

        binding.pokemonSwipeToRefresh.setOnRefreshListener {
            viewModel.addPokemon()
        }
    }

    private fun initTouchListener(pokemonAdapter: PokemonAdapter) {
        val touchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val oldPosition = viewHolder.adapterPosition
                val newPosition = target.adapterPosition
                pokemonAdapter.currentList.add(newPosition, PokemonUiList[oldPosition])
                pokemonAdapter.currentList.removeAt(oldPosition)
                pokemonAdapter.notifyItemMoved(newPosition, oldPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val pokemon = pokemonAdapter.currentList[position]
                viewModel.removePokemon(pokemon.name)
                callUndoAction(position, pokemon)
            }
        }

        ItemTouchHelper(touchHelper).attachToRecyclerView(binding.pokemonRecyclerView)
    }

    private fun callUndoAction(position: Int, pokemonUi: PokemonUi) {
        Snackbar.make(binding.root, "Want to undo the deletion action ?", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                viewModel.addPokemon(pokemonUi)
            }.show()
    }
}