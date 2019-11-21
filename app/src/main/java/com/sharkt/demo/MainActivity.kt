package com.sharkt.demo

import android.os.Bundle
import com.sharkt.api.GithubHttpManager
import com.sharkt.mvvm.BaseMvvmActivity

class MainActivity : BaseMvvmActivity<MainVm>() {
    var http: GithubHttpManager? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GithubHttpManager.init(this, mapOf<String, String>().toMutableMap())
//       GithubHttpManager.getInstance().createAPI()
    }

    override fun init() {
    }

    override fun bind() {
    }


}
