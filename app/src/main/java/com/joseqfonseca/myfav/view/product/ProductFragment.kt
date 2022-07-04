package com.joseqfonseca.myfav.view.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.FragmentProductBinding
import com.joseqfonseca.myfav.model.Product
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val binding: FragmentProductBinding by lazy {
        FragmentProductBinding.inflate(layoutInflater)
    }

    private val productViewModel: ProductViewModel by viewModels()

    private var pointerPicture = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configToolbar()
        setListeners()

        val product = arguments?.get("product") as Product
        productViewModel.product = product

        productViewModel._isFavorite.observe(this, {
            updateFavoriteIcon(it)
        })

        productViewModel._description.observe(this, {
            bindDescription(it)
        })

        bindingComponnents(productViewModel.product)

        productViewModel.loadDescription()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        productViewModel.verifyFavorite()
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
        product.pictures[0]?.let {
            Picasso.get().load(it.url).into(binding.productImageProduct)

            binding.productTextNumberCurrentPicture.text = "1"
            binding.productTextTotalPicture.text = "/${product.pictures.size}"
        }


        //setting the favorite button color
        binding.productBtnFavorite.setImageResource(if (product.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)

        //append the description text with all of product attributes
        product.attributes.forEach {
            binding.productTextAttributes.append("${it.name}: ${it.value_name}\n")
        }
    }

    private fun bindDescription(description: String) {
        //binding product description
        binding.productTextDescripton.let {
            it.text = description
            it.visibility = View.VISIBLE
        }

        //hide loading
        binding.productDescriptionloading.visibility = View.GONE
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

            binding.productTextNumberCurrentPicture.text = (pointerPicture + 1).toString()
        }
    }

    private fun nextPicture() {
        if (pointerPicture < productViewModel.product.pictures.size - 1) {
            Picasso
                .get()
                .load(productViewModel.product.pictures[pointerPicture + 1].url)
                .into(binding.productImageProduct)

            pointerPicture += 1

            binding.productTextNumberCurrentPicture.text = (pointerPicture + 1).toString()
        }
    }

    private fun setFavorite() {
        productViewModel.setFavorite()
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.productBtnFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
    }
}