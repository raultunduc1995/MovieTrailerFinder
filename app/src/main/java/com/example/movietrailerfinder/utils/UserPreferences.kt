package com.example.movietrailerfinder.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.movietrailerfinder.view.activities.MoviesLayout

interface UserPreferences {
  fun setMoviesLayout(layout: MoviesLayout)
  fun getMoviesLayout(): MoviesLayout
  fun clear()
}

class DefaultUserPreferences(private val preferences: SharedPreferences) : UserPreferences {

  companion object {
    private const val MOVIES_LAYOUT_CHOICE_KEY = "up.movies_layout_choice.key"

    lateinit var INSTANCE: UserPreferences

    fun createInstance(context: Context): UserPreferences {
      if (!(::INSTANCE.isInitialized)) {
        INSTANCE = DefaultUserPreferences(PreferenceManager.getDefaultSharedPreferences(context.applicationContext))
      }
      return INSTANCE
    }
  }

  override fun setMoviesLayout(layout: MoviesLayout) {
    preferences.edit()
      .putInt(MOVIES_LAYOUT_CHOICE_KEY, layout.ordinal)
      .apply()
  }

  override fun getMoviesLayout(): MoviesLayout {
    val moviesLayoutOrdinal = preferences.getInt(MOVIES_LAYOUT_CHOICE_KEY, MoviesLayout.NONE.ordinal)
    return MoviesLayout.values()[moviesLayoutOrdinal]
  }

  override fun clear() {
    preferences.edit()
      .remove(MOVIES_LAYOUT_CHOICE_KEY)
      .apply()
  }
}
