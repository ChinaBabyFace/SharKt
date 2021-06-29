package com.sharkt.api

import android.content.Context
import android.util.Log

import com.sharkt.http.core.HttpManager
import com.sharkt.http.interceptor.HeaderInterceptor
import com.sharkt.http.utils.SSLUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GithubHttpManager : HttpManager {
    private var headerParamMap = mapOf<String, String>().toMutableMap()

    private constructor(context: Context) : super(context, BuildConfig.BASE_URL)


    companion object {
        @Volatile
        private var ourInstance: GithubHttpManager? = null

        fun init(context: Context, headerMap: MutableMap<String, String>) =
            ourInstance ?: synchronized(GithubHttpManager::class.java) {
                ourInstance ?: run {
                    ourInstance = GithubHttpManager(context)
                }.run {
                    ourInstance!!.headerParamMap.putAll(headerMap)
                }.run {
                    ourInstance!!.reset()
                }
            }


        fun getInstance(): GithubHttpManager = ourInstance!!

    }

    override fun getHttpClientBuilder(): OkHttpClient.Builder = super
        .getHttpClientBuilder()
        .addInterceptor(HeaderInterceptor(ourInstance!!.headerParamMap))
        .sslSocketFactory(SSLUtils.getTlsSocketFactory(), SSLUtils.getX509TrustManager())
        .connectTimeout(60, TimeUnit.SECONDS)
        .also {
            if (BuildConfig.DEBUG)
                it.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        }


    override fun getRetrofitBuilder(client: OkHttpClient): Retrofit.Builder = super
        .getRetrofitBuilder(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())


}
