package com.sharkt.demo

import android.util.Log
import com.sharkt.api.ApiResultObserver
import com.sharkt.api.ApiService
import com.sharkt.api.BaseDo
import com.sharkt.api.GithubHttpManager
import com.sharkt.demo.databinding.ActivityMainBinding
import com.sharkt.lib_rx_kt.autoSubscribeObserveDisposeTransform
import com.sharkt.mvvm.BaseMvvmActivity


class MainActivity : BaseMvvmActivity<MainVm, ActivityMainBinding>() {
    private lateinit var api: ApiService

    override fun init() {
        Log.e("shark","init");
        GithubHttpManager.init(
            this.applicationContext,
            mapOf<String, String>().toMutableMap()
                .apply { put("Accept", "application/vnd.github.v3+json") })
        api = GithubHttpManager.getInstance().createAPI(ApiService::class.java)

    }

    override fun bind() {
        Log.e("shark","bind");
        vb.textView.text = "start"
        api.getEmojis()
            .autoSubscribeObserveDisposeTransform(this)
            .subscribe(object : ApiResultObserver<Any>() {
                override fun onSuccess(t: BaseDo<Any>) {
                    Log.e("shark","onSuccess");

                }

                override fun onFail(e: Throwable) {
                    Log.e("shark","onFail");

                    vb.textView.text = e.localizedMessage
                }

                override fun onComplete() {
                    Log.e("shark","onComplete");

                    vb.textView.text = "onComplete"
                }

            })
    }
}

