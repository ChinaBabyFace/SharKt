package com.sharkt.mvvm

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<T : BaseVm> : AppCompatActivity() {
    private lateinit var vm: BaseVm

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initViewModel()
        init()
        bind()

    }

    private fun initViewModel() {
        val classT =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        vm = ViewModelProvider(this.viewModelStore, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(classT)
        vm.create()
    }

    fun getVm(): BaseVm {
        return vm
    }

    abstract fun init()
    abstract fun bind()
}