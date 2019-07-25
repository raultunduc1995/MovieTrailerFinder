package com.example.movietrailerfinder.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
  @SerializedName("id")
  val id: String,
  @SerializedName("title")
  val title: String,
  @SerializedName("poster_path")
  val posterPath: String,
  @SerializedName("overview")
  val overview: String
) : Serializable {

}