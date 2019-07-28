package com.example.movietrailerfinder.utils

import com.example.movietrailerfinder.BuildConfig

class ImagesAPiUtil {
  companion object {
    private const val W342_SIZE_PATH = "t/p/w342/"
    private const val W780_SIZE_PATH = "t/p/w780/"

    fun getMediumSizeImageURL(posterPath: String?): String = "${BuildConfig.IMAGES_API_URL}$W342_SIZE_PATH$posterPath"

    fun getLargeSizeImageURL(posterPath: String?) = "${BuildConfig.IMAGES_API_URL}$W780_SIZE_PATH$posterPath"
  }
}
