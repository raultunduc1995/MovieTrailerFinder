package com.example.movietrailerfinder.view.fragments.base

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxFragment: Fragment() {
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