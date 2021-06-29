package com.sharkt.mvvm

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException

object Utils {
    fun <K : ViewBinding?> createViewBinding(vb: Class<K>, inflater: LayoutInflater?): K =
        Class.forName(vb.name).getMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as K

}