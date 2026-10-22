package com.example.tareatresapis

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory

class MainApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .build()
}
