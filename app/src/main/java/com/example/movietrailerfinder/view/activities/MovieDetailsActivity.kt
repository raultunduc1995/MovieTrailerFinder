package com.example.movietrailerfinder.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.repository.model.Movie
import com.example.movietrailerfinder.utils.addFragment
import com.example.movietrailerfinder.utils.setToolbar
import com.example.movietrailerfinder.utils.showHomeFromToolbar
import com.example.movietrailerfinder.view.fragments.MovieDetailsFragment

class MovieDetailsActivity : AppCompatActivity() {

  companion object {

    private const val MOVIE_KEY = "mda.movie.key"

    fun start(context: Context?, movie: Movie) {
      val intent = Intent(context, MovieDetailsActivity::class.java)
      intent.putExtra(MOVIE_KEY, movie)
      context?.startActivity(intent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val movieDetails = intent.getParcelableExtra<Movie>(MOVIE_KEY)
    setContentView(R.layout.activity_main)
    setupToolbar()
    if (savedInstanceState == null) {
      addFragment(R.id.container, MovieDetailsFragment.newInstance(movieDetails))
    }
  }

  private fun setupToolbar() {
    val toolbar: Toolbar = this@MovieDetailsActivity.findViewById(R.id.toolbar)
    setToolbar(toolbar)
    showHomeFromToolbar()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    super.onOptionsItemSelected(item)
    if (item.itemId == android.R.id.home) {
      finish()
      return true
    }
    return false
  }
}