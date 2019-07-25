package com.example.movietrailerfinder.view.activities.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxActivity: AppCompatActivity() {

  private val subscriptions = CompositeDisposable()

  fun subscribe(disposable: Disposable): Disposable {
    subscriptions.add(disposable)
    return disposable
  }

  override fun onStop() {
    super.onStop()
    subscriptions.clear()
  }
}