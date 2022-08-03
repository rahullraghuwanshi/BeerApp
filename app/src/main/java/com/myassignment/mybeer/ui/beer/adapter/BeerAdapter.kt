package com.myassignment.mybeer.ui.beer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myassignment.mybeer.R
import com.myassignment.mybeer.databinding.BeerItemBinding
import com.myassignment.mybeer.model.Beer
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class BeerAdapter : PagingDataAdapter<Beer, BeerAdapter.BeerViewHolder>(NewsDiffUtil) {

    private lateinit var context : Context

    inner class BeerViewHolder(val binding: BeerItemBinding) : RecyclerView.ViewHolder(binding.root)

    object NewsDiffUtil : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = getItem(position)!!
        holder.binding.apply {
            Glide.with(root.context).load(beer.image_url)
                .placeholder(R.drawable.beer_holder)
                .error(R.drawable.beer_holder)
                .into(imgBeer)
            txtName.text = beer.name
            txtTagline.text = beer.tagline
            txtFirstBrewed.text = beer.first_brewed

            root.setOnClickListener {
                onItemClickListener?.let {
                    it(beer)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = BeerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return BeerViewHolder(binding)
    }

    private var onItemClickListener: ((Beer) -> Unit)? = null

    fun setOnItemClickListener(listener: (Beer) -> Unit) {
        onItemClickListener = listener
    }

}