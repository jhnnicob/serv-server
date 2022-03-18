package com.wktsoft.serv.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


class ResourceUtil {

    private var appContext: Context? = null

    companion object {
        private var instance: ResourceUtil? = null

        fun getInstance(): ResourceUtil? {
            return if (instance == null) ResourceUtil().also { instance = it } else instance
        }
    }

    fun init(context: Context) {
        appContext = context
    }

    fun getDrawable(id: Int): Drawable? {
        return appContext?.let { ContextCompat.getDrawable(it, id) }
    }

    fun getColor(id: Int): Int? {
        return appContext?.let { ContextCompat.getColor(it, id) }
    }
}