package com.norman1.consumer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norman1.consumer.data.models.User
import com.norman1.consumer.databinding.FragmentDetailsBinding


private const val USER = "USER"

class DetailsFragment : BottomSheetDialogFragment() {

    private lateinit var user: User
    private lateinit var binding: FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(USER)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        binding.data = user
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, user)
                }
            }
    }
}