package com.digitals.realpokemonapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.digitals.realpokemonapp.adapter.PokemonAdapter
import com.digitals.realpokemonapp.databinding.ActivityMainBinding
import com.digitals.realpokemonapp.mock.pokemonList
import com.digitals.realpokemonapp.model.Pokemon

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
        initTouchListener(pokemonAdapter)

        binding.pokemonSwipeToRefresh.setOnRefreshListener {
            pokemonList.add(
                0,
                Pokemon(
                    "Squirtle",
                    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/007.png",
                    R.color.teal_700
                )
            )
            pokemonAdapter.notifyItemInserted(0)
            binding.pokemonSwipeToRefresh.isRefreshing = false
            binding.pokemonRecyclerView.smoothScrollToPosition(0)
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
                pokemonList.add(newPosition, pokemonList[oldPosition])
                pokemonList.removeAt(oldPosition)
                pokemonAdapter.notifyItemMoved(newPosition, oldPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                pokemonList.removeAt(position)
                pokemonAdapter.notifyItemRemoved(position)
            }
        }

        ItemTouchHelper(touchHelper).attachToRecyclerView(binding.pokemonRecyclerView)
    }
}