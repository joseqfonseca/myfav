package com.joseqfonseca.myfav.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joseqfonseca.myfav.databinding.ItemFavoriteBinding
import com.joseqfonseca.myfav.model.Product
import com.squareup.picasso.Picasso

class FavoriteRecyclerAdapter(
    val itemListener: (product: Product) -> Unit,
    val removeListener: (productId: String) -> Unit
) : RecyclerView.Adapter<FavoriteRecyclerAdapter.FavoriteRecyclerVW>() {

    private val listFavorites = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecyclerVW {
        val item = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteRecyclerVW(item)
    }

    override fun onBindViewHolder(holder: FavoriteRecyclerVW, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int = listFavorites.size

    fun updateList(list: List<Product>) {
        listFavorites.clear()
        listFavorites.addAll(list)
        notifyDataSetChanged()
    }

    inner class FavoriteRecyclerVW(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.itemFavoriteTextTitle.text = product.title
            binding.itemFavoriteTextPrice.text = product.price
            Picasso.get().load(product.thumbnail).into(binding.itemFavoriteImageView)

            binding.root.setOnClickListener {
                itemListener(product)
            }

            binding.itemFavoriteBtnRemove.setOnClickListener {
                removeListener(product.id)
            }
        }
    }
}