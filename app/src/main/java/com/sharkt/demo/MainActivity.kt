package com.sharkt.mvvm.demo

import android.os.Bundle
import com.sharkt.mvvm.BaseMvvmActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvvmActivity<MainVm>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.text = "Hello world kotlin"
    }

    override fun init() {
    }

    override fun bind() {
    }


}
