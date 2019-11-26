package com.sharkt.lib_rx_kt

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.uber.autodispose.autoDispose


fun <T> Observable<T>.autoSubscribeObserveDisposeTransform(owner: LifecycleOwner): ObservableSubscribeProxy<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .autoDispose(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY))
}

