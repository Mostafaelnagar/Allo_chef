package app.te.alo_chef.presentation.base

import android.app.Activity
import android.content.IntentSender.SendIntentException
import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class ImmediateUpdateActivity(val activity: Activity) {
    var appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(activity)
    val TAG = "ImmediateUpdateActivity"

    init {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        Log.d(TAG, appUpdateInfoTask.toString())
        appUpdateInfoTask.addOnSuccessListener {
            Log.d(TAG, "init: " + it.updateAvailability())
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                Log.d(TAG, "update available")
            }
            if (it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Log.d(TAG, "AppUpdateType IMMEDIATE")
            }
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        it,
                        AppUpdateType.IMMEDIATE,
                        activity,
                        UPDATE_REQUEST_CODE
                    )
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                    Log.e(TAG, "init: " + e.message)
                }
            }
        }.addOnFailureListener { e: Exception ->
            Log.e(
                TAG,
                "onFailure: " + e.message
            )
        }
    }

    companion object {
        val UPDATE_REQUEST_CODE = 381
    }

    @JvmName("getAppUpdateManager1")
    fun getAppUpdateManager(): AppUpdateManager {
        return appUpdateManager
    }

}