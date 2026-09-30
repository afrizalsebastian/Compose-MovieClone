package com.github.afrizalsebastian.compose_movieclone.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.afrizalsebastian.compose_movieclone.BuildConfig

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YoutubePlayer(
    videoId: String,
    modifier: Modifier = Modifier,
){
    val baseUrl: String = BuildConfig.YT_BASE_URL
    val fullPath = "$baseUrl/embed/$videoId"

    val headers = mapOf(
        "Referer" to baseUrl,
        "Origin" to baseUrl
    )

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f/9f),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )

                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                }

                webChromeClient = WebChromeClient()
                webViewClient = WebViewClient()

                loadUrl(fullPath, headers)
            }
        },
        update = { webView ->
            webView.loadUrl(fullPath, headers)
        }
    )
}