package com.example.movietrailerfinder.utils

import android.app.Activity
import android.view.View

class FullscreenHelper(private val context: Activity, private val views: List<View>) {

  fun enterFullScreen(viewId: String) {
    val decorView = context.window.decorView

    hideSystemUi(decorView)

    for (view in views) {
      if (view.tag != viewId) {
        view.visibility = View.GONE
        view.invalidate()
      }
    }
  }

  fun exitFullScreen(viewId: String) {
    val decorView = context.window.decorView

    showSystemUi(decorView)

    for (view in views) {
      if (view.tag != viewId) {
        view.visibility = View.VISIBLE
        view.invalidate()
      }
    }
  }

  private fun hideSystemUi(mDecorView: View) {
    mDecorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
  }

  private fun showSystemUi(mDecorView: View) {
    mDecorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
  }
}
