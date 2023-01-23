package com.ihsan.news_app.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.navArgs
import com.ihsan.news_app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_webview_news.*

class WebviewNewsFragment : Fragment() {
    private val args: WebviewNewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_webview_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.loadUrl(args.url)
        webview.webViewClient = WebViewClient()
    }
}