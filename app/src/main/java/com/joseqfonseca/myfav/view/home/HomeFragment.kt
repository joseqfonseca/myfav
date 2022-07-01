package com.joseqfonseca.myfav.view.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.FragmentHomeBinding
import com.joseqfonseca.myfav.lib.Utils
import com.joseqfonseca.myfav.model.Product

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = HomeViewModel(activity?.getPreferences(Context.MODE_PRIVATE)!!)

        configToolbar()
        configRecyclerView()

        search("music")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.verifyFavorites()
    }

    private fun configToolbar() {

        binding.homeToolbar.let {
            it.findViewById<SearchView>(R.id.home_btn_search).let { searchView ->
                searchView.maxWidth = Int.MIN_VALUE
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


            it.menu.findItem(R.id.home_btn_favorite).setOnMenuItemClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                false
            }
        }
    }

    private fun configRecyclerView() {
        val recycler = binding.recyclerView

        val adapter = HomeRecyclerAdapter(
            { openProductFragment(it) },
            { setFavorite(it) }
        )
        recycler.adapter = adapter

        homeViewModel._listProduct.observe(this, {
            it?.let {
                binding.textNotFound.visibility = if (it.size > 0) View.GONE else View.VISIBLE
                binding.homeLoading.visibility = View.GONE
            }

            adapter.updateList(it)
        })

    }

    private fun openProductFragment(product: Product) {
        findNavController().navigate(
            R.id.action_homeFragment_to_productFragment,
            bundleOf(Pair("product", product))
        )
    }

    private fun setFavorite(productId: String) {
        homeViewModel.setFavorite(productId)
    }

    private fun search(word: String) {
        binding.homeLoading.visibility = View.VISIBLE

        homeViewModel.searchProductByFirstCategoryPredict(word)
    }

}