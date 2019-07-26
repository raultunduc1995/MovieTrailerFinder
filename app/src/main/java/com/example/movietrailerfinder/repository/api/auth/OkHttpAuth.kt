package com.example.movietrailerfinder.repository.api.auth

import okhttp3.Interceptor
import okhttp3.Response

object HttpAuth {
  const val CONTENT_TYPE_HEADER = "Content-Type"
}

class AddAuthHeaderInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    var request = chain.request()
    request = request.newBuilder()
      .addHeader(HttpAuth.CONTENT_TYPE_HEADER, "application/json")
      .build()

    return chain.proceed(request)
  }
}