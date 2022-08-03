package com.myassignment.mybeer.ui.beer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.myassignment.mybeer.R
import com.myassignment.mybeer.data.NetworkInstance
import com.myassignment.mybeer.databinding.ActivityBeerBinding
import com.myassignment.mybeer.ui.beer.adapter.BeerAdapter
import com.myassignment.mybeer.ui.beer.viewmodel.BeerViewModel
import com.myassignment.mybeer.ui.beer.viewmodel.BeerViewModelProviderFactory
import kotlinx.coroutines.launch

class BeerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBeerBinding
    private lateinit var viewModel: BeerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer)

        //for dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        val beerRepository = BeerRepository()
        val viewModelProviderFactory = BeerViewModelProviderFactory(beerRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(BeerViewModel::class.java)


        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val adapter = BeerAdapter()
        adapter.setOnItemClickListener {
           Toast.makeText(
               this@BeerActivity,
               "Item:${it.name}",
               Toast.LENGTH_LONG
           ).show()
        }

        val layoutManager = LinearLayoutManager(this@BeerActivity)

        binding.rvBeer.adapter = adapter
        binding.rvBeer.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.getBeer()
                .observe(this@BeerActivity, Observer {
                    it?.let {
                        adapter.submitData(lifecycle, it)
                    }
                })
        }


        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.progressBar.visibility = View.VISIBLE
            else {
                binding.progressBar.visibility = View.GONE
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this@BeerActivity, it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }

    }
}