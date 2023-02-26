package app.te.alo_chef.presentation.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import app.te.alo_chef.presentation.base.BaseActivity
import app.te.alo_chef.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import app.te.alo_chef.R

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var nav: NavController
    companion object {
        const val ACTION_OPEN_SPECIFIC_PAGE = "ACTION_OPEN_SPECIFIC_PAGE"
        const val TAB_ID = "TAB_ID"
    }
    private var isReceiverRegistered = false

    private val openSpecificTabReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override
        fun onReceive(context: Context, intent: Intent) {
            navigateToSpecificTab(intent.getIntExtra(TAB_ID, 0))
        }
    }
    override
    fun getLayoutId() = R.layout.activity_home

    override
    fun setUpBottomNavigation() {
        setUpNavigationWithGraphs()
    }

    private fun setUpNavigationWithGraphs() {
        val graphIds = listOf(
            R.navigation.nav_home,
            R.navigation.nav_search,
            R.navigation.nav_account
        )

        val controller = binding.bottomNavigationView.setupWithNavController(
            graphIds,
            supportFragmentManager,
            R.id.fragment_host_container,
            intent
        )

        navController = controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host_container) as NavHostFragment
        nav = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.vipFragment,
                R.id.favoriteFragment,
                R.id.home_fragment,
                R.id.languageFragment,
                R.id.moreFragment,
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(nav, appBarConfiguration)
        binding.bottomNavigationView.apply {
            selectedItemId = R.id.home_fragment
            setupWithNavController(nav)
        }
        navChangeListener()

    }

    private fun navChangeListener() {
        nav.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment, R.id.favoriteFragment,
                R.id.moreFragment,
                R.id.vipFragment,
                R.id.accountFragment,
                R.id.productDetailsFragment,
                R.id.profileFragment,
                R.id.mealsFilterDialog,
                -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.GONE
                }
                R.id.paymentFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    binding.toolbar.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(nav) || super.onOptionsItemSelected(item)
    }

    private fun registerOpenSpecificTabReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this)
                .registerReceiver(
                    openSpecificTabReceiver,
                    IntentFilter(ACTION_OPEN_SPECIFIC_PAGE)
                )
            isReceiverRegistered = true
        }
    }

    override
    fun onResume() {
        super.onResume()
        registerOpenSpecificTabReceiver()
    }

    private fun navigateToSpecificTab(@IdRes tabID: Int) {
        binding.bottomNavigationView.selectedItemId = tabID
    }

    override
    fun onDestroy() {
        unregisterOpenSpecificTabReceiver()

        super.onDestroy()
    }

    private fun unregisterOpenSpecificTabReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(openSpecificTabReceiver)
    }
}