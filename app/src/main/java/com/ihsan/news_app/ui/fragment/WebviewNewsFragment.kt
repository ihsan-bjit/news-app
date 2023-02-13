package com.ihsan.news_app.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.ihsan.news_app.databinding.FragmentWebviewNewsBinding

class WebviewNewsFragment : Fragment() {
    private val args: WebviewNewsFragmentArgs by navArgs()
    private lateinit var binding:FragmentWebviewNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentWebviewNewsBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webview=binding.webview
        webview.loadUrl(args.url)
        webview.webViewClient = WebViewClient()
    }
}