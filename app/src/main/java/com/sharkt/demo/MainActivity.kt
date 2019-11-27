package com.sharkt.demo

import android.os.Bundle
import com.sharkt.api.GithubHttpManager
import com.sharkt.mvvm.BaseMvvmActivity


class MainActivity : BaseMvvmActivity<MainVm>() {
    var http: GithubHttpManager? = null
    var headMp = mapOf<String, String>().toMutableMap()
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        headMp["Accept"] = "application/vnd.github.v3+json"
//
//        GithubHttpManager.init(this.applicationContext, headMp)
//        var api = GithubHttpManager.getInstance()!!.createAPI(ApiService::class.java)
//        api.getEmojis()
//            .autoSubscribeObserveDisposeTransform(this)
//            .subscribe(object : ApiResultObserver<Any>() {
//                override fun onSuccess(t: BaseDo<Any>) {
//                }
//
//                override fun onFail(e: Throwable) {
//                }
//
//                override fun onComplete() {
//                }
//
//            })
    }

    override fun init() {

    }

    override fun bind() {
    }
}

