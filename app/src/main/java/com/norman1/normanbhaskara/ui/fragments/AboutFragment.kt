package com.norman1.normanbhaskara.ui.fragments

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norman1.normanbhaskara.databinding.FragmentAboutBinding
import com.norman1.normanbhaskara.databinding.FragmentAboutBinding.inflate

class AboutFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sourceUrl.setOnClickListener {
            startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://github.com/inspiratia/androidgithub")
                )
            )
        }
    }
}