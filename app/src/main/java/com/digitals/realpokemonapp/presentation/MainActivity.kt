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
            Log.e("TAG", "onCreate: ${listPokemon.size}", )
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
                PokemonUiList.add(newPosition, PokemonUiList[oldPosition])
                PokemonUiList.removeAt(oldPosition)
                pokemonAdapter.notifyItemMoved(newPosition, oldPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                PokemonUiList.removeAt(position)
                pokemonAdapter.notifyItemRemoved(position)
            }
        }

        ItemTouchHelper(touchHelper).attachToRecyclerView(binding.pokemonRecyclerView)
    }
}