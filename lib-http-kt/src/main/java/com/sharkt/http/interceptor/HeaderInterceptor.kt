package com.sharkt.http.interceptor

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by renyuxiang on 2015/12/3.
 */
class HeaderInterceptor(private val headerMap: Map<String, String>?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (headerMap == null || headerMap.isEmpty()) {
            return chain.proceed(chain.request())
        }

        val builder = chain.request().newBuilder()
        for ((key, value) in headerMap) {
            builder.addHeader(key, value)
        }

        return chain.proceed(builder.build())
    }
}
