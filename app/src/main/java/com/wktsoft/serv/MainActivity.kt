package com.wktsoft.serv

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wktsoft.serv.databinding.ActivityMainBinding
import com.wktsoft.serv.event.OnEventNavigateMainMenu
import com.wktsoft.serv.event.OnEventShowBottomNav
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        binding.navView.setupWithNavController(navController)
        binding.navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.dashboardFragment)
                }
                R.id.navigation_order_list -> {
                    navController.navigate(R.id.orderListFragment)
                }
                R.id.navigation_accounts -> {
                    navController.navigate(R.id.accountsFragment)
                }
                R.id.navigation_products -> {
                    navController.navigate(R.id.productListFragment)
                }
            }
            true
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    @Subscribe
    fun onEventNavigateMainMenu(event : OnEventNavigateMainMenu) {
        binding.navView.selectedItemId = event.getSelectedMenuId()
    }

    @Subscribe
    fun onEventShowBottomNav(event : OnEventShowBottomNav) {
        if (event.getIsNavVisible()) {
            binding.navView.visibility = VISIBLE
        } else {
            binding.navView.visibility = GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

}