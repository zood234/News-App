package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebView : AppCompatActivity() {
    private val webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        val webView = findViewById<android.webkit.WebView>(R.id.webViewArticle)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(articleClicked.articleClicked)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }
}