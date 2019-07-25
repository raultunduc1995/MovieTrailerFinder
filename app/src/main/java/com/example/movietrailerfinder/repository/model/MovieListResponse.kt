package com.example.movietrailerfinder.repository.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
  @SerializedName("page")
  val page: Int,
  @SerializedName("total_results")
  val totalResults: Int,
  @SerializedName("total_pages")
  val totalPages: Int,
  @SerializedName("results")
  val results: List<Movie>
)