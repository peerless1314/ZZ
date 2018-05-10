package com.zz

import android.app.Application
import org.xutils.x

/**
 * Created by 测试 on 2018/5/10.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        x.Ext.init(this)
        x.Ext.setDebug(false)
    }
}