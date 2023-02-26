package app.te.alo_chef.presentation.home

import android.view.MenuItem
import android.view.View
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
    lateinit var nav: NavController

    override
    fun getLayoutId() = R.layout.activity_home

    override
    fun setUpBottomNavigation() {
        setUpNavigationWithGraphs()
    }

    private fun setUpNavigationWithGraphs() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host_container) as NavHostFragment
        nav = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.vipFragment,
                R.id.favoriteFragment,
                R.id.home_fragment,
                R.id.accountFragment,
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
}