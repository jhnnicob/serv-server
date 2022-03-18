package com.wktsoft.serv.event

class OnEventNavigateMainMenu(private val selectedMenuId: Int) {
    fun getSelectedMenuId(): Int {
        return selectedMenuId
    }
}