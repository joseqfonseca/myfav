package com.joseqfonseca.myfav.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joseqfonseca.myfav.databinding.ItemHomeBinding
import com.joseqfonseca.myfav.model.Category
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter(
    private val itemListener: (categoryId: String) -> Unit
) : RecyclerView.Adapter<HomeRecyclerAdapter.RecyclerViewHomeViewHolder>() {

    private val productList = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHomeViewHolder {
        return RecyclerViewHomeViewHolder(
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHomeViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size!!

    fun updateList(list: List<Category>?) {
        productList.clear()
        list?.let {
            productList.addAll(it)
        }
        notifyDataSetChanged()
    }

    inner class RecyclerViewHomeViewHolder(val item: ItemHomeBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(category: Category) {
            item.itemHomeTitle.text = category.name
            Picasso.get().load(category.picture).into(item.itemHomeImage)

            item.root.setOnClickListener {
                itemListener(category.name)
            }
        }

    }

}