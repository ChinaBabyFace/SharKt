package com.sharkt.mvvm

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sharkt.mvvm.Utils.createViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<T : BaseVm, K : ViewBinding> : AppCompatActivity() {
    lateinit var vm: T
    lateinit var vb: K

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val typeArray = (javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments
        vb = createViewBinding(typeArray[1] as Class<K>, layoutInflater)
        if (vb !is K) {
            finish()
            return;
        }
        setContentView(vb.root)
        vm = ViewModelProvider(this)[typeArray[0] as Class<T>]
        vm.create()

        init()
        bind()
    }

    abstract fun init()
    abstract fun bind()
}