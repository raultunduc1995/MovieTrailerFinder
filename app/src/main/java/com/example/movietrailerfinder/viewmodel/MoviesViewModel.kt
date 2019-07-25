package com.example.movietrailerfinder.viewmodel

import com.example.movietrailerfinder.BuildConfig
import com.example.movietrailerfinder.repository.api.ApiProvider
import com.example.movietrailerfinder.repository.model.Movie
import io.reactivex.Flowable

class MoviesViewModel {

  companion object {
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
      .map { result ->
        Thread.sleep(5000)
        result.results
      }
}
