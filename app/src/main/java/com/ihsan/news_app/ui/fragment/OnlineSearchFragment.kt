package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihsan.news_app.R
import com.ihsan.news_app.databinding.FragmentOnlineSearchBinding

class OnlineSearchFragment : Fragment() {
    private lateinit var binding:FragmentOnlineSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOnlineSearchBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }
}