package com.example.movietrailerfinder.repository.api

import com.example.movietrailerfinder.repository.model.MovieListResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

  @GET("3/movie/top_rated?language=en-US&page=1")
  fun getMovies(@Query("api_key") apiKey: String): Flowable<MovieListResponse>
}
