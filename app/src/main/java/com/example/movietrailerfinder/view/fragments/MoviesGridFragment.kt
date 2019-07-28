package com.example.movietrailerfinder.view.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.repository.model.Movie
import com.example.movietrailerfinder.utils.*
import com.example.movietrailerfinder.view.activities.MovieDetailsActivity
import com.example.movietrailerfinder.view.fragments.base.RxFragment
import com.example.movietrailerfinder.viewmodel.MoviesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movies_grid.*
import kotlinx.android.synthetic.main.fragment_movies_grid_item.view.*

class MoviesGridFragment : RxFragment() {

  companion object {
    private const val TAG = "MoviesGridFragment"
    private const val RECYCLER_VIEW_STATE_KEY = "mgf.recycler_view_state.key"
  }

  private lateinit var adapter: MovieImagesAdapter
  private var recyclerViewState: Parcelable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (savedInstanceState != null) {
      recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    (activity as AppCompatActivity).setTitleOnToolbar(R.string.movies_list_title)
    return inflater.inflate(R.layout.fragment_movies_grid, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val pagerSnapHelper = PagerSnapHelper()
    adapter = MovieImagesAdapter(context, mutableListOf(), this::onMovieSelected)
    moviesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    moviesList.adapter = adapter
    pagerSnapHelper.attachToRecyclerView(moviesList)
  }

  private fun onMovieSelected(movie: Movie) {
    MovieDetailsActivity.start(context, movie)
  }

  override fun onStart() {
    super.onStart()
    subscribeToMoviesList()
  }

  private fun subscribeToMoviesList() {
    Log.d(TAG, "Subscribe to movies data stream...")
    showLoading()
    subscribe(
      MoviesViewModel.getInstance()
        .getMoviesDataFlowable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ movies ->
          dismissLading()
          updateMoviesData(movies)
        }, { error ->
          dismissLading()
          Log.e(TAG, error.message, error)
        })
    )
  }

  private fun showLoading() {
    moviesList.hide()
    progressBar.show()
  }

  private fun dismissLading() {
    moviesList.show()
    progressBar.hide()
  }

  private fun updateMoviesData(movies: List<Movie>) {
    adapter.data.clear()
    adapter.data.addAll(movies)
    adapter.notifyDataSetChanged()
    recyclerViewState?.let { moviesList.layoutManager?.onRestoreInstanceState(it) }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    recyclerViewState = moviesList.layoutManager?.onSaveInstanceState()
    outState.putParcelable(RECYCLER_VIEW_STATE_KEY, recyclerViewState)
  }
}

class MovieImagesAdapter(
  private val context: Context?,
  val data: MutableList<Movie>,
  val onMovieSelected: (movie: Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
    return MovieImagesHolder(LayoutInflater.from(context).inflate(R.layout.fragment_movies_grid_item, parent, false))
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, index: Int) {
    if (viewHolder is MovieImagesHolder) {
      viewHolder.bind(data[index])
    }
  }

  inner class MovieImagesHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(data: Movie) {
      view.setOnClickListener { onMovieSelected(data) }
      view.movieTitle.text = data.title
      Glide.with(context!!)
        .load(ImagesAPiUtil.getLargeSizeImageURL(data.posterPath))
        .listener(object : RequestListener<Drawable> {
          override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
          ): Boolean {
            view.progressBar.remove()
            return false
          }

          override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
          ): Boolean {
            view.progressBar.remove()
            view.moviePosterBackground.background = context.resources.getDrawable(R.drawable.image_border, null)
            return false
          }
        })
        .error(R.drawable.icon_error)
        .into(view.moviePoster)
    }
  }
}
