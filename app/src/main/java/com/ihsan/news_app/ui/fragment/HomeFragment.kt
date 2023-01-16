package com.ihsan.news_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ihsan.news_app.R
import com.ihsan.news_app.databinding.FragmentHomeBinding
import com.ihsan.news_app.viewmodel.NewsviewViewModel


class HomeFragment : Fragment() {
    private val viewModel: NewsviewViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentHomeBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

}