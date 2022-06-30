package com.joseqfonseca.myfav.view.favorite

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.compose.ui.input.key.KeyEvent
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.FragmentFavoriteBinding
import com.joseqfonseca.myfav.lib.Utils
import com.joseqfonseca.myfav.model.Product

class FavoriteFragment : Fragment() {

    private val binding: FragmentFavoriteBinding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }

    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var adapter: FavoriteRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configToolbar()
        setTextSearchListener()

        favoriteViewModel = FavoriteViewModel(activity?.getPreferences(Context.MODE_PRIVATE)!!)

        adapter = FavoriteRecyclerAdapter(
            { openProductFragment(it) },
            { removeFavorite(it) }
        )

        binding.favoriteRecyclerView.adapter = adapter

        favoriteViewModel._listFavorites.observe(this, {
            it?.let {
                binding.favoriteTextNotFound.visibility =
                    if (it.size > 0) View.GONE else View.VISIBLE
                binding.favoriteLoading.visibility = View.GONE
            }

            adapter.updateList(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun configToolbar() {
        binding.favoriteToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite_btn_search -> {
                    binding.favoriteTextSearch.run {
                        isVisible = !isVisible
                        requestFocus()
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun setTextSearchListener() {
        binding.favoriteTextSearch.addTextChangedListener(
            onTextChanged = { text, b, c, d ->
                favoriteViewModel.filterByWord(text.toString())
            }
        )
    }

    private fun openProductFragment(product: Product) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_productFragment,
            bundleOf(Pair("product", product))
        )
    }

    private fun removeFavorite(productId: String) {
        favoriteViewModel.removeFavorite(productId)
    }

    private fun loadFavorites() {
        binding.favoriteLoading.visibility = View.VISIBLE

        favoriteViewModel.loadFavorites()
    }

}