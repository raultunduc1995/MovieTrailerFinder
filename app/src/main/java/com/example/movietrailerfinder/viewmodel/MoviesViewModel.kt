package com.example.movietrailerfinder.viewmodel

import com.example.movietrailerfinder.BuildConfig
import com.example.movietrailerfinder.repository.api.ApiProvider
import com.example.movietrailerfinder.repository.model.Movie
import io.reactivex.Flowable

class MoviesViewModel {

  companion object {
    private val YOUTUBE_VIDEOS_IDS = mutableListOf(
      "_swJQVGuauw",
      "T22yr9PG-Ig",
      "Tg6HGcj7pGo",
      "NxwJ1q7cIck",
      "z5WrgDzNIZ0",
      "GX8Hg6kWQYI"
    )
    private lateinit var INSTANCE: MoviesViewModel

    fun getInstance(): MoviesViewModel {
      if (!(::INSTANCE.isInitialized)) {
        INSTANCE = MoviesViewModel()
      }
      return INSTANCE
    }
  }

  fun getMoviesDataFlowable(): Flowable<List<Movie>> =
    ApiProvider.getMoviesApi()
      .getMovies(BuildConfig.MOVIES_API_KEY)
      .map { result -> result.results }

  fun getYoutubeVideos(): List<String> = YOUTUBE_VIDEOS_IDS
}
