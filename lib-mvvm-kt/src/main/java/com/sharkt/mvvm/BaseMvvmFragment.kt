package com.sharkt.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmFragment<T : BaseVm?, B : ViewBinding?> : Fragment() {
    var vm: T? = null
    var vb: B? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val typeArray = (javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments
        vb = Utils.createViewBinding(typeArray[1] as Class<B>, layoutInflater)
        vm = ViewModelProvider(this)[typeArray[0] as Class<T>]
        vm!!.create()
        init()
        bind()
        return if (vb == null) null else vb!!.root
    }

    protected abstract fun init()
    protected abstract fun bind()
}