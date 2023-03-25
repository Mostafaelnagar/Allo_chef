package app.te.alo_chef.presentation.base.extensions

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import app.te.alo_chef.R
import app.te.alo_chef.domain.utils.FailureStatus
import app.te.alo_chef.domain.utils.Resource.Failure
import app.te.alo_chef.presentation.auth.AuthActivity
import app.te.alo_chef.presentation.base.DeepLinks
import app.te.alo_chef.presentation.base.utils.*
import app.te.alo_chef.presentation.base.utils.Constants.MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS
import app.te.alo_chef.presentation.base.utils.Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE


fun Fragment.handleApiError(
    failure: Failure,
    retryAction: (() -> Unit)? = null,
    noDataAction: (() -> Unit)? = null,
    notActive: (() -> Unit)? = null,
    notActiveAction: (() -> Unit)? = null,
    noInternetAction: (() -> Unit)? = null
) {
    when (failure.failureStatus) {
        FailureStatus.EMPTY -> {
            noDataAction?.invoke()
            failure.message?.let {
                if (it == Constants.NOT_MATCH_PASSWORD.toString())
                    showNoApiErrorAlert(
                        requireActivity(), getString(R.string.not_match_password)
                    )
                else
                    showNoApiErrorAlert(requireActivity(), it)
            }
        }
        FailureStatus.NO_INTERNET -> {
            noInternetAction?.invoke()
            showNoInternetAlert(requireActivity())
        }
        FailureStatus.NOT_ACTIVE -> {
            notActive?.invoke()
            failure.message?.let {
                showNoApiErrorAlert(
                    requireActivity(),
                    it,
                )
            }
        }
        FailureStatus.TOKEN_EXPIRED -> {
            noDataAction?.invoke()
            showNoApiErrorAlert(requireActivity(), getString(R.string.log_out))
            openActivityAndClearStack(AuthActivity::class.java)
        }

        else -> {
            noDataAction?.invoke()
            requireView().showSnackBar(
                failure.message ?: resources.getString(R.string.some_error),
                resources.getString(R.string.retry),
                retryAction
            )
        }
    }
}

fun Fragment.hideKeyboard() = hideSoftInput(requireActivity())

fun Fragment.showNoInternetAlert() = showNoInternetAlert(requireActivity())

fun Fragment.showMessage(message: String?) = showMessage(requireContext(), message)

fun Fragment.showError(
    message: String,
    retryActionName: String? = null,
    action: (() -> Unit)? = null
) =
    requireView().showSnackBar(message, retryActionName, action)

fun Fragment.getMyColor(@ColorRes id: Int) = ContextCompat.getColor(requireContext(), id)

fun Fragment.getMyDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(requireContext(), id)!!
fun Fragment.getMyDrawableVector(@DrawableRes id: Int) =
    ContextCompat.getDrawable(requireContext(), id)!!

fun Fragment.getMyString(id: Int) = resources.getString(id)

fun <A : Activity> Fragment.openActivityAndClearStack(activity: Class<A>) {
    requireActivity().openActivityAndClearStack(activity)
}

fun <A : Activity> Fragment.openActivity(activity: Class<A>) {
    requireActivity().openActivity(activity)
}

fun <A : Activity> Fragment.openIntentActivity(activity: Class<A>, extra: Int) {
    requireActivity().openIntentActivity(activity, extra)
}

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.removeNavigationResultObserver(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)

fun <T> Fragment.setNavigationResult(result: MutableLiveData<T>, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result.value)
    backToPreviousScreen()
}

fun Fragment.onBackPressedCustomAction(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override
            fun handleOnBackPressed() {
                action()
            }
        }
    )
}

fun Fragment.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
    findNavController().navigate(directions, navOptions)
}

fun Fragment.navigateSafe(deepLink: String) {
    findNavController().navigate(Uri.parse(deepLink), navOptions = DeepLinks.setNavOptions())
}

fun Fragment.backToPreviousScreen() {
    findNavController().navigateUp()
}


fun checkNotificationsPermissions(activity: Activity, operation: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //to simplify, call requestPermissions again
                Toast.makeText(
                    activity,
                    activity.getString(R.string.permission_dialog_content1),
                    Toast.LENGTH_LONG
                ).show()

                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS
                )
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS
                )
            }
        } else {
            // permission granted
            operation()
        }
    }
}

fun checkStoragePermissions(activity: Activity, operation: () -> Unit) {
    if (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            //to simplify, call requestPermissions again
            Toast.makeText(
                activity,
                activity.getString(R.string.permission_dialog_content1),
                Toast.LENGTH_LONG
            ).show()
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        }
    } else {
        // permission granted
        operation()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun checkStorage13Permissions(activity: Activity, operation: () -> Unit) {
    if (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_MEDIA_IMAGES
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
        Log.e("checkStoragePermissions", "checkStoragePermissions: ")
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        ) {
            Log.e("checkStoragePermissions", "shouldShowRequestPermissionRationale: ")
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            //to simplify, call requestPermissions again
            Toast.makeText(
                activity,
                activity.getString(R.string.permission_dialog_content1),
                Toast.LENGTH_LONG
            ).show()
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            Log.e("checkStoragePermissions", "else: ")
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        }
    } else {
        Log.e("checkStoragePermissions", "operation : ")

        // permission granted
        operation()
    }
}

