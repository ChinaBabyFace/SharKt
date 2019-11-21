package com.sharkt.api

import android.content.Context

import com.sharkt.http.core.HttpManager
import com.sharkt.http.interceptor.HeaderInterceptor
import com.sharkt.http.utils.SSLUtils
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by renyuxiang on 2017/6/8.
 */

class WjyHttpManager private constructor(context: Context) : HttpManager(context, BuildConfig.BASE_URL) {
    private var headerParamMap: MutableMap<String, String>? = null

    fun appendHeader(key: String, value: String) {
        ourInstance!!.headerParamMap!![key] = value
    }

    override fun getHttpClientBuilder(): OkHttpClient.Builder {
        val builder = super.getHttpClientBuilder()
        builder.addInterceptor(HeaderInterceptor(ourInstance!!.headerParamMap))
        builder.sslSocketFactory(SSLUtils.getTlsSocketFactory(), SSLUtils.getX509TrustManager())
            .connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder
    }

    override fun getRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        val builder = super.getRetrofitBuilder(client)
        builder.addConverterFactory(GsonConverterFactory.create())
        return builder
    }

    companion object {
        @Volatile
        private var ourInstance: WjyHttpManager? = null

        fun init(context: Context, headerMap: MutableMap<String, String>) {
            if (ourInstance == null) {
                synchronized(WjyHttpManager::class.java) {
                    if (ourInstance == null) {
                        ourInstance = WjyHttpManager(context)
                    }
                }
            }
            ourInstance!!.headerParamMap = headerMap
        }

        fun getInstance(): WjyHttpManager {
            return ourInstance ?: throw RuntimeException("Should call init() at least one time!")
        }
    }
}
