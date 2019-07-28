package com.example.movietrailerfinder.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.repository.model.Movie
import com.example.movietrailerfinder.utils.hide
import com.example.movietrailerfinder.utils.setTitleOnToolbar
import com.example.movietrailerfinder.utils.show
import com.example.movietrailerfinder.view.activities.MovieDetailsActivity
import com.example.movietrailerfinder.view.fragments.base.RxFragment
import com.example.movietrailerfinder.viewmodel.MoviesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_row.view.*
import kotlinx.android.synthetic.main.fragment_movies_list.*

class MoviesListFragment : RxFragment() {
  companion object {
    private const val TAG = "MoviesListFragment"
  }

  private lateinit var adapter: MoviesAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    (activity as AppCompatActivity).setTitleOnToolbar(R.string.movies_list_title)
    return inflater.inflate(R.layout.fragment_movies_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    adapter = MoviesAdapter(context, mutableListOf(), this::onMovieSelected)
    val layoutManager = LinearLayoutManager(context)
    moviesList.layoutManager = layoutManager
    val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
    moviesList.addItemDecoration(dividerItemDecoration)
    moviesList.adapter = adapter
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
  }
}

class MoviesAdapter(
  private val context: Context?,
  val data: MutableList<Movie>,
  val onMovieSelected: (movie: Movie) -> Unit
) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
    return MoviesHolder(LayoutInflater.from(context).inflate(R.layout.fragment_movie_row, p0, false))
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
    if (viewHolder is MoviesHolder) {
      viewHolder.bind(data[position])
    }
  }

  inner class MoviesHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(data: Movie) {
      view.movieId.text = data.id
      view.movieTitle.text = data.title
      view.setOnClickListener { onMovieSelected(data) }
    }
  }
}
