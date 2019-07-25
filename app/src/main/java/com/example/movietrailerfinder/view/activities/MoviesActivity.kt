package com.example.movietrailerfinder.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.utils.addFragment
import com.example.movietrailerfinder.utils.hideHomeFromToolbar
import com.example.movietrailerfinder.utils.setToolbar
import com.example.movietrailerfinder.view.fragments.MoviesListFragment

class MoviesActivity : AppCompatActivity() {
  companion object {
    private const val LAYOUT_MOVIES_PARAM_KEY = "ma.layout_movies.key"

    fun start(context: Context, layout: MoviesLayout) {
      val intent = Intent(context, MoviesActivity::class.java)
      intent.putExtra(LAYOUT_MOVIES_PARAM_KEY, layout.ordinal)
      context.startActivity(intent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val layoutType = getLayoutType()
    setContentView(R.layout.activity_main)
    setToolbar(findViewById(R.id.toolbar))
    hideHomeFromToolbar()
    if (savedInstanceState == null) {
      attachAppropriateLayout(layoutType)
    }
  }

  private fun getLayoutType(): MoviesLayout {
    val layoutOrdinal = intent.getIntExtra(LAYOUT_MOVIES_PARAM_KEY, -1)
    return MoviesLayout.values()[layoutOrdinal]
  }


  private fun attachAppropriateLayout(layoutType: MoviesLayout) {
    when (layoutType) {
      MoviesLayout.LIST -> {
        addFragment(R.id.container, MoviesListFragment())
      }
      MoviesLayout.GRID -> {

      }
      else -> throw IllegalStateException("No layout was set")
    }
  }
}
