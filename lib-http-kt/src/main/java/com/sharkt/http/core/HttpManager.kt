package com.sharkt.http.core


import android.content.Context

import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit


/**
 * **HttpManager。**
 *
 * **详细说明：**
 *
 * 无。
 *
 * **修改列表：**
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 *
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-12-3 </td><td>建立类型</td></tr>
 * <tr><td>2</td><td>Renyuxiang</td><td>2019-11-19 下午2:22:00</td><td>迁移到Kotlin</td></tr>
 * </table> *
 *
 * @author Renyuxiang
 * @version 2.0
 * @since 1.0
 */
abstract class HttpManager {
    private lateinit var retrofit: Retrofit
    private  var context: Context
    private  var baseUrl: String

    constructor(context: Context, baseUrl: String) {
        this.context = context
        this.baseUrl = baseUrl
        reset()
    }

    open fun getHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, 10 * 1024 * 1024/*10MB*/))
    }

    open fun getRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().client(client).baseUrl(baseUrl!!)
    }

    fun <S> createAPI(apiClass: Class<S>): S {
        return retrofit.create(apiClass)
    }

    fun reset() {
        retrofit = getRetrofitBuilder(getHttpClientBuilder().build()).build()
    }
}
