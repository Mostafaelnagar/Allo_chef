package app.te.alo_chef.presentation.auth

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import app.te.alo_chef.R
import app.te.alo_chef.presentation.base.BaseActivity
import app.te.alo_chef.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    private lateinit var nav: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override
    fun getLayoutId() = R.layout.activity_auth
    override fun setUpViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        nav = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        binding.toolbar.apply {
            setupWithNavController(nav, appBarConfiguration)
        }
        val navGraph = nav.navInflater.inflate(R.navigation.nav_auth)
        if (intent.extras != null && intent.hasExtra("extra")) {
            val destination = intent.getIntExtra("extra", R.id.splashFragment)
            navGraph.setStartDestination(destination)
            nav.graph = navGraph
        }
        navChangeListener()
    }

    private fun navChangeListener() {
        nav.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashFragment
                || destination.id == R.id.tutorialFragment
                || destination.id == R.id.logInFragment
                || destination.id == R.id.signUpFragment
            ) {
                binding.toolbar.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (nav.navigateUp())
            nav.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        else {
            finish()
            false
        }
    }
}