package com.norman1.normanbhaskara.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.norman1.normanbhaskara.R
import com.norman1.normanbhaskara.databinding.FragmentHomeBinding
import com.norman1.normanbhaskara.ui.adapters.UsersAdapter
import com.norman1.normanbhaskara.ui.viewmodels.HomeViewModel
import com.norman1.normanbhaskara.utils.Status

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeAdapter: UsersAdapter
    private val homeViewModel: HomeViewModel by navGraphViewModels(R.id.my_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.errLayout.emptyText.text = resources.getString(R.string.search_hint)

        homeAdapter = UsersAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                HomeFragmentDirections.detailsAction(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        }
        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }

        binding.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.setUserId(query)
                    binding.searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        observeData()
    }

    private fun observeData() {
        homeViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { users ->
                            if (!users.isNullOrEmpty()) {
                                showSuccess()
                                homeAdapter.setData(users)
                            } else {
                                showError(null)
                            }
                        }
                    }

                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.ERROR -> {
                        showError(it.message)
                    }
                }
            }
        })
    }

    private fun showError(message: String?) {
        binding.errLayout.main.visibility = View.VISIBLE
        binding.errLayout.emptyText.text = message ?: resources.getString(R.string.not_found)
        binding.progress.visibility = View.GONE
        binding.recyclerHome.visibility = View.GONE
    }

    private fun showSuccess() {
        binding.errLayout.main.visibility = View.GONE
        binding.progress.visibility = View.GONE
        binding.recyclerHome.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.errLayout.main.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
        binding.recyclerHome.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                findNavController().navigate(HomeFragmentDirections.settingsAction())
                return true
            }
        }
        return false
    }
}
