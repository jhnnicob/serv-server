package com.wktsoft.serv.event

class OnEventShowBottomNav(private val isNavVisible: Boolean) {
    fun getIsNavVisible() : Boolean {
        return isNavVisible
    }
}