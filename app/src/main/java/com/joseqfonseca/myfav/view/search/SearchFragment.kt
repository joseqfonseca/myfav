package com.joseqfonseca.myfav.view.search

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.FragmentSearchBinding
import com.joseqfonseca.myfav.model.Product

class SearchFragment : Fragment() {

    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel = SearchViewModel(activity?.getPreferences(Context.MODE_PRIVATE)!!)

        configToolbar()
        configRecyclerView()

        val categoryFromHome = arguments?.getString("categoryName")

        categoryFromHome?.let {
            binding.searchToolbar.findViewById<SearchView>(R.id.search_btn_search)
                .let { searchView ->
                    searchView.setQuery(it, true)
                }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        searchViewModel.verifyFavorites()
    }

    private fun configToolbar() {


        binding.searchToolbar.let {
            it.findViewById<SearchView>(R.id.search_btn_search).let { searchView ->
                searchView.maxWidth = Int.MIN_VALUE
                searchView.onActionViewExpanded()
                searchView.queryHint = getString(R.string.home_text_btnSearch)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(word: String?): Boolean {
                        word?.let {
                            search(it)
                            searchView.clearFocus()
                        }
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        return false
                    }
                })

            }


            it.menu.findItem(R.id.search_btn_favorite).setOnMenuItemClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_favoriteFragment)
                false
            }
        }
    }

    private fun configRecyclerView() {
        val recycler = binding.searchRecyclerView

        val adapter = SearchRecyclerAdapter(
            { openProductFragment(it) },
            { setFavorite(it) }
        )
        recycler.adapter = adapter

        searchViewModel._listProduct.observe(this, {
            it?.let {
                binding.searchTextNotFound.visibility = if (it.size > 0) View.GONE else View.VISIBLE
                binding.searchLoading.visibility = View.GONE
            }

            adapter.updateList(it)
        })

    }

    private fun openProductFragment(product: Product) {
        findNavController().navigate(
            R.id.action_searchFragment_to_productFragment,
            bundleOf(Pair("product", product))
        )
    }

    private fun setFavorite(productId: String) {
        searchViewModel.setFavorite(productId)
    }

    private fun search(word: String) {
        binding.searchLoading.visibility = View.VISIBLE

        searchViewModel.searchProductByFirstCategoryPredict(word)
    }

}