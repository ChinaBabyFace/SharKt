package com.sharkt.api

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class ApiResultObserver<T> : Observer<BaseDo<T>> {

     override fun onSubscribe(d: Disposable) {
     }

     override fun onNext(t: BaseDo<T>) {
         onSuccess(t)
     }

     override fun onError(e: Throwable) {
         onFail(e)
     }

    abstract fun onSuccess(t: BaseDo<T>)
    abstract fun onFail(e: Throwable)


 }