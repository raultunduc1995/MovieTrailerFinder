package com.example.movietrailerfinder.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.repository.model.Movie
import com.example.movietrailerfinder.utils.setToolbar
import com.example.movietrailerfinder.utils.showHomeFromToolbar
import com.example.movietrailerfinder.utils.startActivityOfClass
import com.example.movietrailerfinder.view.activities.base.RxActivity

class MovieDetailsActivity : RxActivity() {

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
    setContentView(R.layout.activity_main)
    setupToolbar()
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