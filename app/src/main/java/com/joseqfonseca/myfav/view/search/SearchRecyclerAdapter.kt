package com.joseqfonseca.myfav.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.ItemProductBinding
import com.joseqfonseca.myfav.model.Product
import com.squareup.picasso.Picasso

class SearchRecyclerAdapter(
    private val itemListener: (product: Product) -> Unit,
    private val favoriteListener: (productId: String) -> Unit
) : RecyclerView.Adapter<SearchRecyclerAdapter.RecyclerViewHomeViewHolder>() {

    private val productList = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHomeViewHolder {
        return RecyclerViewHomeViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHomeViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(list: List<Product>?) {
        productList.clear()
        list?.let {
            productList.addAll(it)
        }
        notifyDataSetChanged()
    }

    inner class RecyclerViewHomeViewHolder(val item: ItemProductBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(product: Product) {
            item.itemProductTextTitle.text = product.title
            item.itemProductTextPrice.text = String.format("%.2f", product.price.toDouble())
            item.itemProductBtnFavorite.setImageResource(if (product.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
            Picasso.get().load(product.secure_thumbnail).into(item.itemProductImageView)

            item.root.setOnClickListener {
                itemListener(product)
            }

            item.itemProductBtnFavorite.setOnClickListener {
                favoriteListener(product.id)
            }
        }

    }

}