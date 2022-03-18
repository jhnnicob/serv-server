package com.wktsoft.serv

import android.app.Application
import com.wktsoft.serv.util.ResourceUtil

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ResourceUtil.getInstance()?.init(this)
    }
}