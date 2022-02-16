package com.example.feednews2022

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.Log.INFO
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.util.Base64.getEncoder
import java.util.logging.Level.INFO

class MainActivity : AppCompatActivity()
{
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webViewer)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://feednews.me/")

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                // setProgressDialogVisibility(true)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                // isLoaded = true
                // setProgressDialogVisibility(false)
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
//                isLoaded = false
//                val errorMessage = "Got Error! $error"
//                showToast(errorMessage)
//                infoTV.text = errorMessage
//                setProgressDialogVisibility(false)
                super.onReceivedError(view, request, error)
            }
        }

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                }
            }
        }
    }


    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            val webView: WebView = findViewById(R.id.webViewer)


            Log.println(Log.WARN, null, "TESTTESTTEST")
            Log.println(Log.WARN, null, it)

            val feedUrl = "https://feednews.me/android-share/"
            val base64 = Base64.encodeToString(it.toByteArray(), Base64.DEFAULT)
            val fullUrl = feedUrl.plus(base64)

            Log.println(Log.WARN, null, fullUrl.replace("https://", ""))

            webView.loadUrl(fullUrl)
        }
    }
}


