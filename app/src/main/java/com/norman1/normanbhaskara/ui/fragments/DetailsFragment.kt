package com.norman1.normanbhaskara.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.color.MaterialColors
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialContainerTransform
import com.norman1.normanbhaskara.R
import com.norman1.normanbhaskara.data.models.User
import com.norman1.normanbhaskara.databinding.FragmentDetailsBinding
import com.norman1.normanbhaskara.ui.viewmodels.DetailViewModel
import com.norman1.normanbhaskara.utils.Status

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var user: User
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var detailViewModel: DetailViewModel
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(
            this
        ).get(DetailViewModel::class.java)

        val transformation: MaterialContainerTransform = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host
            interpolator = FastOutSlowInInterpolator()
            containerColor = MaterialColors.getColor(
                requireActivity().findViewById(android.R.id.content),
                R.attr.colorSurface
            )
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            duration = 300
        }
        sharedElementEnterTransition = transformation
        sharedElementReturnTransition = transformation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        observeData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.content.transitionName = args.Username
        binding.fav.setOnClickListener { addOrRemoveFavorite() }

        val tabList = arrayOf(
            resources.getString(R.string.followers),
            resources.getString(R.string.following)
        )
        pagerAdapter = PagerAdapter(tabList, args.Username, this)
        binding.pager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun observeData() {
        detailViewModel.data(args.Username).observe(viewLifecycleOwner, {
            if (it.status == Status.SUCCESS) {
                user = it.data!!
                binding.data = it.data
            }
        })

        detailViewModel.isFavorite.observe(viewLifecycleOwner, { fav ->
            isFavorite = fav
            changeFavoriteUi(fav)
        })
    }

    private fun changeFavoriteUi(checked: Boolean) {
        if (checked) {
            binding.fav.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.fav.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun addOrRemoveFavorite() {
        if (!isFavorite) {
            detailViewModel.addFavorite(user)
            Toast.makeText(
                context,
                resources.getString(R.string.favorite_add, user.login),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            detailViewModel.removeFavorite(user)
            Toast.makeText(
                context,
                resources.getString(R.string.favorite_remove, user.login),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = tabList.size

        override fun createFragment(position: Int): Fragment =
            FollowFragment.newInstance(username, tabList[position])
    }
}
