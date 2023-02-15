package app.te.alo_chef.core.notifications.notification_manager

import android.content.Context
import android.util.Log
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability

object HmsGmsUtil {

    fun isHmsAvailable(context: Context?): Boolean {
        var isAvailable = false
        if (null != context) {
            val result =
                HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context)
            isAvailable = 0 == result
        }
        Log.e("HmsGmsUtil", "isHmsAvailable: $isAvailable")
        return isAvailable
    }

    fun isGmsAvailable(context: Context?): Boolean {
        var isAvailable = false
        if (null != context) {
            val result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
            isAvailable = 0 == result
        }
        Log.e("HmsGmsUtil", "isGmsAvailable: $isAvailable")
        return isAvailable
    }

    fun isOnlyHms(context: Context?): Boolean {
        return isHmsAvailable(context) && !isGmsAvailable(context)
    }
}