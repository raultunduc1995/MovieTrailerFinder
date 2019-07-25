package com.example.movietrailerfinder.utils

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
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

fun AppCompatActivity.replaceFragment(
  viewId: Int,
  fragment: Fragment,
  addToBackStack: Boolean = false,
  backStackTag: String? = null
) {
  val tx = supportFragmentManager.beginTransaction().replace(viewId, fragment, backStackTag)
  if (addToBackStack) tx.addToBackStack(backStackTag)
  tx.commit()
}

inline fun <reified T> Activity.startActivityOfClass(activityClass: Class<T>) {
  startActivity(Intent(this, activityClass))
}

fun AppCompatActivity.showAlert(titleResId: Int, messageResId: Int, positiveButton: Int, positiveClickListener: DialogInterface.OnClickListener?,
                                negativeButton: Int, negativeClickListener: DialogInterface.OnClickListener? = null): AlertDialog {
  val alertBuilder = AlertDialog.Builder(this)
  alertBuilder.setTitle(titleResId)
  alertBuilder.setMessage(messageResId)
  alertBuilder.setPositiveButton(positiveButton, positiveClickListener)
  alertBuilder.setNegativeButton(negativeButton, negativeClickListener)
  val alert = alertBuilder.create()
  alert.show()
  return alert
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
