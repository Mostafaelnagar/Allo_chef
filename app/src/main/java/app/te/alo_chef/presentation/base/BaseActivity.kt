package app.te.alo_chef.presentation.base

import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import app.te.alo_chef.presentation.base.ImmediateUpdateActivity.Companion.UPDATE_REQUEST_CODE
import app.te.alo_chef.presentation.base.extensions.adjustFontScale
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.zeugmasolutions.localehelper.LocaleHelper
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegate
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegateImpl
import java.util.*

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    private val localeDelegate: LocaleHelperActivityDelegate = LocaleHelperActivityDelegateImpl()
    private var _binding: VB? = null
    open val binding get() = _binding!!
    lateinit var navController: LiveData<NavController>
    lateinit var immediateUpdateActivity: ImmediateUpdateActivity

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(localeDelegate.attachBaseContext(newBase))
    }

    override
    fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        val context = super.createConfigurationContext(overrideConfiguration)
        return LocaleHelper.onAttach(context)
    }

    override
    fun getApplicationContext(): Context =
        localeDelegate.getApplicationContext(super.getApplicationContext())

    override
    fun onResume() {
        super.onResume()
        localeDelegate.onResumed(this)
        checkForUpdate()
    }

    override
    fun onPause() {
        super.onPause()
        localeDelegate.onPaused()
    }

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localeDelegate.onCreate(this)
        adjustFontScale()
        initViewBinding()
        setContentView(binding.root)
        immediateUpdateActivity = ImmediateUpdateActivity(this)
        if (savedInstanceState == null) {
            setUpBottomNavigation()
            setUpNavigationDrawer()
        }


        setUpViews()
    }

    private fun checkForUpdate() {

        immediateUpdateActivity.getAppUpdateManager().appUpdateInfo
            .addOnSuccessListener { it ->
                if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        immediateUpdateActivity.getAppUpdateManager()
                            .startUpdateFlowForResult(
                                it,
                                AppUpdateType.IMMEDIATE,
                                this,
                                UPDATE_REQUEST_CODE
                            )
                    } catch (e: SendIntentException) {
                        e.printStackTrace()
                    }
                }
            }
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigation()
        setUpNavigationDrawer()
    }

    private fun initViewBinding() {
        _binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun setUpBottomNavigation() {}
    open fun setUpNavigationDrawer() {}

    open fun setUpViews() {}

    open fun updateLocale(language: String) {
        localeDelegate.setLocale(this, Locale(language))
    }

    override
    fun onSupportNavigateUp(): Boolean {
        return navController.value?.navigateUp()!! || super.onSupportNavigateUp()
    }

    override
    fun getDelegate() = localeDelegate.getAppCompatDelegate(super.getDelegate())

}