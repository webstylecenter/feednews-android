package com.example.feednews2022

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity()
{
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        when {
            intent?.action == Intent.ACTION_SEND -> {
                Log.println(Log.WARN, null, "Handle send Text");
                if ("text/plain" == intent.type) {
                    handleSendText2(intent, savedInstanceState) // Handle text being sent
                }
            } else -> {
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
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }

                    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                        super.onReceivedError(view, request, error)
                    }
                }
            }
        }
    }


    private fun handleSendText(intent: Intent) {
        Log.println(Log.WARN, null, "Handle send Text");
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

    private fun handleSendText2(intent: Intent, savedInstanceState: Bundle?) {
        Log.println(Log.WARN, null, "Handle send Text");
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val webView: WebView = findViewById(R.id.webViewer)
            webView.settings.javaScriptEnabled = true

            val feedUrl = "https://feednews.me/android-share/"
            val base64 = Base64.encodeToString(it.toByteArray(), Base64.DEFAULT)
            val fullUrl = feedUrl.plus(base64)

            webView.loadUrl(fullUrl)

            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val url = request?.url.toString()
                    view?.loadUrl(url)
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Thread.sleep(3_000)
                    exitProcess(0);
                }

                override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                    super.onReceivedError(view, request, error)
                }
            }
        }
    }
}


