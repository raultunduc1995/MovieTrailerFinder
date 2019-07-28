package com.example.movietrailerfinder.view.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.repository.model.Movie
import com.example.movietrailerfinder.utils.*
import com.example.movietrailerfinder.viewmodel.MoviesViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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
  private val views = mutableListOf<View>()
  private lateinit var fullscreenHelper: FullscreenHelper

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
    bindMovieData()
    initYoutubeVideos()
    views.add(movieDetails)
    fullscreenHelper = FullscreenHelper(this.activity!!, views)
  }

  private fun bindMovieData() {
    movieId.text = movie?.id
    movieTitle.text = movie?.title
    movieDescription.text = movie?.overview
    Glide.with(context!!)
      .load(ImagesAPiUtil.getMediumSizeImageURL(movie?.posterPath))
      .placeholder(R.drawable.progress)
      .error(R.drawable.icon_error)
      .into(moviePoster)
  }

  private fun initYoutubeVideos() {
    MoviesViewModel.getInstance()
      .getYoutubeVideos()
      .forEach { youtubeVideo ->
        val youtubePlayerView = YouTubePlayerView(context!!)
        youtubePlayerView.layoutParams = getYoutubePlayerLayoutParams()
        youtubePlayerView.tag = youtubeVideo
        youtubePlayerView.addYouTubePlayerListener(this.provideYouTubePLayerListener(youtubePlayerView, youtubeVideo))
        lifecycle.addObserver(youtubePlayerView)
        movieDetailsContainer.addView(youtubePlayerView)
        views.add(youtubePlayerView)
      }
  }

  private fun getYoutubePlayerLayoutParams(): FrameLayout.LayoutParams {
    val layoutParams =
      FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    layoutParams.topMargin = 10
    layoutParams.bottomMargin = 10
    layoutParams.gravity = Gravity.CENTER_VERTICAL
    return layoutParams
  }

  private fun provideYouTubePLayerListener(
    youtubePlayerView: YouTubePlayerView,
    youtubeVideo: String
  ): AbstractYouTubePlayerListener {
    return object : AbstractYouTubePlayerListener() {
      override fun onReady(youTubePlayer: YouTubePlayer) {
        super.onReady(youTubePlayer)
        youTubePlayer.cueVideo(youtubeVideo, -1f)
        attachFullScreenListener(youtubePlayerView)
      }

      override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
        super.onStateChange(youTubePlayer, state)
        if (state == PlayerConstants.PlayerState.PLAYING) {
          youtubePlayerView.enterFullScreen()
        }
      }
    }
  }

  private fun attachFullScreenListener(youtubePlayerView: YouTubePlayerView) {
    youtubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
      override fun onYouTubePlayerEnterFullScreen() {
        (activity as AppCompatActivity).hideToolbar()
        fullscreenHelper.enterFullScreen(youtubePlayerView.tag as String)
      }

      override fun onYouTubePlayerExitFullScreen() {
        fullscreenHelper.exitFullScreen(youtubePlayerView.tag as String)
        (activity as AppCompatActivity).showToolbar()
      }
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    views.forEach {
      if (it is YouTubePlayerView) {
        it.release()
      }
    }
  }
}
