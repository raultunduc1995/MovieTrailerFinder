package com.example.movietrailerfinder.view.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.movietrailerfinder.R
import com.example.movietrailerfinder.utils.hideHomeFromToolbar
import com.example.movietrailerfinder.utils.setToolbar

enum class MoviesLayout {
  NONE,
  LIST,
  GRID
}

class MovieOptionActivity : AppCompatActivity() {

  companion object {
    const val TAG = "MovieOptionActivity"
  }

  private var moviesLayoutSelectionAlert: AlertDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupToolbar()
  }

  private fun setupToolbar() {
    val toolbar: Toolbar = this@MovieOptionActivity.findViewById(R.id.toolbar)
    setToolbar(toolbar)
    hideHomeFromToolbar()
  }

  override fun onStart() {
    super.onStart()
    showLayoutSelectionDialog()
  }

  private fun showLayoutSelectionDialog() {
    moviesLayoutSelectionAlert =
      PromptAlertDialog(this,
        {
          Log.d(TAG, "List view selected")
          closeLayoutSelectionDialog()
          navigateToMovies(MoviesLayout.LIST)
        },
        {
          Log.d(TAG, "Grid view selected")
          closeLayoutSelectionDialog()
          navigateToMovies(MoviesLayout.GRID)
        }
      )
    moviesLayoutSelectionAlert?.show()
  }

  private fun navigateToMovies(layout: MoviesLayout) {
    finish()
    MoviesActivity.start(this, layout)
  }

  override fun onStop() {
    super.onStop()
    closeLayoutSelectionDialog()
  }

  private fun closeLayoutSelectionDialog() {
    moviesLayoutSelectionAlert?.let {
      with(it) {
        if (isShowing) {
          cancel()
        }
      }
    }
    moviesLayoutSelectionAlert = null
  }
}

class PromptAlertDialog(
  context: Context,
  private val onListSelected: () -> Unit,
  private val onGridSelected: () -> Unit
) :
  AlertDialog(context) {
  init {
    setCancelable(false)
    setTitle(R.string.layout_selection_alert_title)
    val dialogLayout = layoutInflater.inflate(R.layout.alert_movies_layout_selection, null)
    setView(dialogLayout)
    dialogLayout.findViewById<Button>(R.id.selectListButton)
      .setOnClickListener { onListSelected.invoke() }
    dialogLayout.findViewById<Button>(R.id.selectGridButton)
      .setOnClickListener { onGridSelected.invoke() }
  }
}
