package com.example.movietrailerfinder.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movietrailerfinder.BuildConfig
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.repository.model.Movie
import com.example.movietrailerfinder.utils.remove
import com.example.movietrailerfinder.utils.setTitleOnToolbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment() {
  companion object {

    private const val MOVIE_KEY = "mdf.movie.key"

    fun newInstance(movie: Movie): Fragment {
      val fragment = MovieDetailsFragment()
      val bundle = Bundle()
      bundle.putParcelable(MOVIE_KEY, movie)
      fragment.arguments = bundle
      return fragment
    }
  }

  private var movie: Movie? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments?.containsKey(MOVIE_KEY) == true) {
      movie = arguments?.getParcelable(MOVIE_KEY) as Movie
    } else {
      throw IllegalStateException("Movie not found")
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    (activity as AppCompatActivity).setTitleOnToolbar(R.string.movie_details_title)
    return inflater.inflate(R.layout.fragment_movie_details, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    movieId.text = movie?.id
    movieTitle.text = movie?.title
    movieDescription.text = movie?.overview
    Log.d("asf", "${BuildConfig.IMAGES_API_URL}${movie?.posterPath}")
    Picasso.get()
      .load("${BuildConfig.IMAGES_API_URL}${movie?.posterPath}")
      .error(R.drawable.icon_error)
      .into(moviePoster, object: Callback {
        override fun onSuccess() {
          progressBar.remove()
        }

        override fun onError(e: Exception?) {
          progressBar.remove()
        }
      })
  }
}
