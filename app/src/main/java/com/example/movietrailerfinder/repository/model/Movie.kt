package com.example.movietrailerfinder.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Movie(
  @SerializedName("id")
  val id: String,
  @SerializedName("title")
  val title: String,
  @SerializedName("poster_path")
  val posterPath: String,
  @SerializedName("overview")
  val overview: String
) : Parcelable {

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
      override fun createFromParcel(source: Parcel): Movie = Movie(source)
      override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
    }
  }

  constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString()
  )

  override fun writeToParcel(dest: Parcel?, flags: Int) {
    dest?.writeString(id)
    dest?.writeString(title)
    dest?.writeString(posterPath)
    dest?.writeString(overview)
  }

  override fun describeContents(): Int = 0
}