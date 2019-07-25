package com.example.movietrailerfinder.repository.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ImagesApi {

  @GET("t/p/w185/{poster_path}")
  fun getPosterImageFor(@Path("poster_path") posterPath: String)
}
