package app.te.alo_chef.presentation.home

import android.os.Bundle
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
    private lateinit var nav: NavController
    var activeIndex: Int = 2

    override
    fun getLayoutId() = R.layout.activity_home

    override
    fun setUpBottomNavigation() {
        setUpNavigationWithGraphs()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activeIndex = savedInstanceState?.getInt("activeIndex") ?: 2
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
            if (destination.id == R.id.home_fragment
                || destination.id == R.id.favoriteFragment
                || destination.id == R.id.moreFragment
                || destination.id == R.id.vipFragment
                || destination.id == R.id.accountFragment
                || destination.id == R.id.productDetailsFragment
                || destination.id == R.id.profileFragment
                || destination.id == R.id.mealsFilterDialog
            ) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.toolbar.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
                binding.toolbar.visibility = View.VISIBLE
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