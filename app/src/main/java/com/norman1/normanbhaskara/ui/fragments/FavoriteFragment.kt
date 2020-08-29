package com.norman1.normanbhaskara.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.norman1.normanbhaskara.R
import com.norman1.normanbhaskara.databinding.FragmentFavoriteBinding
import com.norman1.normanbhaskara.ui.adapters.UsersAdapter
import com.norman1.normanbhaskara.ui.viewmodels.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    private lateinit var favAdapter: UsersAdapter

    private val favoriteViewModel: FavoriteViewModel by navGraphViewModels(R.id.my_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favAdapter = UsersAdapter(arrayListOf()) { username, iv ->

            findNavController().navigate(
                FavoriteFragmentDirections.favoriteDetailsAction(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        }

        binding.recyclerFav.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
        observeData()
    }

    private fun observeData() {
        showLoading()
        favoriteViewModel.dataFavorite.observe(viewLifecycleOwner, {
            it?.let { users ->
                if (!users.isNullOrEmpty()) {
                    showSuccess()
                    favAdapter.setData(users)
                } else {
                    showError(
                        resources.getString(
                            R.string.no_favourite
                        )
                    )
                }
            }
        })
    }

    private fun showError(message: String?) {
        binding.errLayout.main.visibility = View.VISIBLE
        binding.errLayout.emptyText.text = message ?: resources.getString(R.string.not_found)
        binding.progress.visibility = View.GONE
        binding.recyclerFav.visibility = View.GONE
    }

    private fun showSuccess() {
        binding.errLayout.main.visibility = View.GONE
        binding.progress.visibility = View.GONE
        binding.recyclerFav.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.errLayout.main.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
        binding.recyclerFav.visibility = View.GONE
    }
}