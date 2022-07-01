package com.joseqfonseca.myfav.view.favorite

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.FragmentFavoriteBinding
import com.joseqfonseca.myfav.model.Product

class FavoriteFragment : Fragment() {

    private val binding: FragmentFavoriteBinding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }

    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var adapter: FavoriteRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
        configToolbar()
        //setTextSearchListener()

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

        binding.favoriteToolbar.findViewById<SearchView>(R.id.favorite_btn_search).let {
            it.queryHint = getString(R.string.favorite_text_btnSearch)

            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    favoriteViewModel.filterByWord(newText.toString())
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

            })

            it.setOnCloseListener(object : SearchView.OnCloseListener {
                override fun onClose(): Boolean {
                    favoriteViewModel.filterByWord("")
                    return false
                }
            })
        }


        /*binding.favoriteToolbar.setOnMenuItemClickListener {
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
        }*/
    }

    /*private fun setTextSearchListener() {
        binding.favoriteTextSearch.addTextChangedListener(
            onTextChanged = { text, b, c, d ->
                favoriteViewModel.filterByWord(text.toString())
            }
        )
    }*/

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