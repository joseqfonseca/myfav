package com.joseqfonseca.myfav.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.ItemProductBinding
import com.joseqfonseca.myfav.model.Product
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter(
    val itemListener: (product: Product) -> Unit
) : RecyclerView.Adapter<HomeRecyclerAdapter.RecyclerViewHomeViewHolder>() {

    private val productList = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHomeViewHolder {
        return RecyclerViewHomeViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHomeViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size!!

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
            item.itemProductTitle.text = product.title
            item.itemProductCurrency.text = "R$"
            item.itemProductPrice.text = product.price
            //item.itemProductBtnFavorite.setImageResource(if (product.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
            Picasso.get().load(product.thumbnail).into(item.itemProductImage)

            item.root.setOnClickListener {
                itemListener(product)
            }
        }

    }

}