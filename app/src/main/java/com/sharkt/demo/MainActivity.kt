package com.sharkt.demo

import android.os.Bundle
import com.sharkt.api.ApiService
import com.sharkt.api.BaseDo
import com.sharkt.api.GithubHttpManager
import com.sharkt.api.Single
import com.sharkt.mvvm.BaseMvvmActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class MainActivity : BaseMvvmActivity<MainVm>() {
    var http: GithubHttpManager? = null
    var headMp = mapOf<String, String>().toMutableMap();
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        headMp["Accept"] = "application/vnd.github.v3+json"

        GithubHttpManager.init(this.applicationContext, headMp)
        var api = GithubHttpManager.getInstance()!!.createAPI(ApiService::class.java)
        api.getEmojis()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Observer<BaseDo>{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseDo) {
                }

                override fun onError(e: Throwable) {
                }
            })
//        Observable.just(1).subscribe(object : Observer<Int> {
//            override fun onComplete() {
//            }
//
//            override fun onSubscribe(d: Disposable) {
//            }
//
//            override fun onNext(t: Int) {
//            }
//
//            override fun onError(e: Throwable) {
//            }
//        })

    }

    override fun init() {
    }

    override fun bind() {
    }


}

