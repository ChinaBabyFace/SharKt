package com.sharkt.mvvm

import androidx.lifecycle.ViewModel

abstract class BaseVm : ViewModel() {
    fun create() {
        init()
        bind()
    }

    abstract fun init()
    abstract fun bind()
}