package com.joseqfonseca.myfav.view.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joseqfonseca.myfav.R
import com.joseqfonseca.myfav.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val homeViewModel: HomeViewModel by viewModels()

    lateinit private var adapter: HomeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configToolbar()
        configRecyclerView()

        loadCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadCategories()
    }

    private fun configToolbar() {

        binding.homeToolbar.let {
            it.findViewById<SearchView>(R.id.home_btn_search).let { searchView ->
                searchView.maxWidth = Int.MIN_VALUE
                searchView.queryHint = getString(R.string.home_text_btnSearch)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(word: String?): Boolean {
                        word?.let {
                            openSearchFragment(it)
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
        val recycler = binding.homeRecyclerView

        adapter = HomeRecyclerAdapter(
            { openSearchFragment(it) }
        )

        recycler.adapter = adapter

        homeViewModel._listCategory.observe(this, {
            it?.let {
                binding.homeTextNotFound.visibility = if (it.size > 0) View.GONE else View.VISIBLE
                binding.homeTextEnjoy.visibility = if (it.size > 0) View.VISIBLE else View.GONE
                binding.homeLoading.visibility = View.GONE
            }

            adapter.updateList(it)
        })

    }

    private fun openSearchFragment(categoryName: String) {
        findNavController().navigate(
            R.id.action_homeFragment_to_searchFragment,
            bundleOf(Pair("categoryName", categoryName))
        )
    }

    private fun loadCategories() {
        binding.homeLoading.visibility = View.VISIBLE

        homeViewModel.loadCategories()
    }

}