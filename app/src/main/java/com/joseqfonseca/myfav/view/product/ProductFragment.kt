package com.joseqfonseca.myfav.view.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joseqfonseca.myfav.databinding.FragmentProductBinding
import com.joseqfonseca.myfav.model.Product
import com.squareup.picasso.Picasso

class ProductFragment : Fragment() {

    val binding: FragmentProductBinding by lazy {
        FragmentProductBinding.inflate(layoutInflater)
    }

    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product = arguments?.get("product") as Product

        productViewModel = ProductViewModel(product)

        bindingComponnents(productViewModel.product)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private fun bindingComponnents(product: Product) {
        binding.productTextTitle.text = product.title
        binding.productTextPrice.text = String.format("%.2f", product.price.toDouble())
        binding.productTextPriceParcel.text = "10x " + String.format("%.2f", product.price.toDouble().div(10)) + " sem juros"
        //Picasso.get().load(product.pictures[0].url).into(binding.productImageProduct)

    }

}