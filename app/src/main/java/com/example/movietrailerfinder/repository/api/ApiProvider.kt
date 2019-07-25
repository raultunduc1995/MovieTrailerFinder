package com.example.movietrailerfinder.repository.api

import com.example.movietrailerfinder.BuildConfig
import com.example.movietrailerfinder.repository.api.auth.AddAuthHeaderInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {
  companion object {

    private lateinit var MOVIES_API: MoviesApi
    private lateinit var IMAGES_API: ImagesApi

    fun getMoviesApi(): MoviesApi {
      if (!::MOVIES_API.isInitialized) {
        val retrofit = Retrofit.Builder()
          .client(provideHttpClient())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BuildConfig.MOVIES_API_URL)
          .build()
        MOVIES_API = retrofit.create(MoviesApi::class.java)
      }
      return MOVIES_API
    }

    fun getImagesApi(): ImagesApi {
      if (!::IMAGES_API.isInitialized) {
        val retrofit = Retrofit.Builder()
          .client(provideHttpClient())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BuildConfig.IMAGES_API_URL)
          .build()
        IMAGES_API = retrofit.create(IMAGES_API::class.java)
      }
      return IMAGES_API
    }

    private fun provideHttpClient(): OkHttpClient =
      OkHttpClient.Builder()
        .addInterceptor(AddAuthHeaderInterceptor())
        .build()
  }
}
