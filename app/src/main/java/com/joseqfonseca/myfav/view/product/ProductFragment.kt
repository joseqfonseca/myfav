package com.joseqfonseca.myfav.view.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.FragmentProductBinding
import com.joseqfonseca.myfav.model.Product
import com.squareup.picasso.Picasso

class ProductFragment : Fragment() {

    val binding: FragmentProductBinding by lazy {
        FragmentProductBinding.inflate(layoutInflater)
    }

    lateinit var productViewModel: ProductViewModel

    private var pointerPicture = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configToolbar()

        val product = arguments?.get("product") as Product

        productViewModel = ProductViewModel(product, requireContext())

        setListeners()

        bindingComponnents(productViewModel.product)

        productViewModel._product.observe(this, {
            updateFavoriteIcon(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private fun configToolbar() {
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.product_toolbar_btn_favorite -> {
                    findNavController().navigate(R.id.action_productFragment_to_favoriteFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun bindingComponnents(product: Product) {
        binding.productTextTitle.text = product.title
        binding.productTextPrice.text = String.format("%.2f", product.price.toDouble())
        binding.productTextPriceParcel.text =
            "10x " + String.format("%.2f", product.price.toDouble().div(10)) + " sem juros"

        //rendering the first picture
        product.pictures[0].let {
            Picasso.get().load(it.url).into(binding.productImageProduct)
        }

        //setting the favorite button color
        binding.productBtnFavorite.setImageResource(if (product.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)

        //append the description text with all of product attributes
        product.attributes.forEach {
            binding.productTextDescripton.append("\n${it.name}: ${it.value_name}")
        }
    }

    private fun setListeners() {
        binding.productBtnPreviousImage.setOnClickListener {
            previousPicture()
        }

        binding.productBtnNextImage.setOnClickListener {
            nextPicture()
        }

        binding.productBtnFavorite.setOnClickListener {
            setFavorite()
        }
    }

    private fun previousPicture() {
        if (pointerPicture > 0) {
            Picasso
                .get()
                .load(productViewModel.product.pictures[pointerPicture - 1].url)
                .into(binding.productImageProduct)

            pointerPicture -= 1
        }
    }

    private fun nextPicture() {
        if (pointerPicture < productViewModel.product.pictures.size - 1) {
            Picasso
                .get()
                .load(productViewModel.product.pictures[pointerPicture + 1].url)
                .into(binding.productImageProduct)

            pointerPicture += 1
        }
    }

    private fun setFavorite() {
        productViewModel.setFavorite()
    }

    private fun updateFavoriteIcon(product: Product) {
        binding.productBtnFavorite.setImageResource(if (product.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
    }
}