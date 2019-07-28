package com.example.movietrailerfinder.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.movietrailerfinder.R

fun AppCompatActivity.setToolbar(toolbar: Toolbar) {
  setSupportActionBar(toolbar)
  supportActionBar?.setDisplayShowTitleEnabled(false)
}

fun AppCompatActivity.showToolbar() {
  supportActionBar?.show()
}

fun AppCompatActivity.hideToolbar() {
  supportActionBar?.hide()
}

fun AppCompatActivity.showHomeFromToolbar() {
  supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

fun AppCompatActivity.hideHomeFromToolbar() {
  supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

fun AppCompatActivity.setTitleOnToolbar(title: String) {
  val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
  toolbarTitle.text = title
}

fun AppCompatActivity.setTitleOnToolbar(titleRes: Int) {
  val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
  toolbarTitle.text = getString(titleRes)
}

fun AppCompatActivity.addFragment(
  viewId: Int,
  fragment: Fragment,
  addToBackStack: Boolean = false,
  backStackTag: String? = null
) {
  val tx = supportFragmentManager.beginTransaction().add(viewId, fragment)
  if (addToBackStack) tx.addToBackStack(backStackTag)
  tx.commit()
}

fun View.show() {
  visibility = View.VISIBLE
}

fun View.hide() {
  visibility = View.INVISIBLE
}

fun View.remove() {
  visibility = View.GONE
}
