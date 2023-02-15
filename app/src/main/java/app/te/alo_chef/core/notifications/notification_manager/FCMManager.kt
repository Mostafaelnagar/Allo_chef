package app.te.alo_chef.core.notifications.notification_manager

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object FCMManager {
    private var AR_TOPIC = "AllUsersArabic"
    private var EN_TOPIC = "AllUsersEnglish"

    private fun subscribeTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
    }

    private fun unSubscribeTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
    }

    fun generateFCMToken(context: Context, callback: (String) -> Unit) {

        when {
            HmsGmsUtil.isGmsAvailable(context) -> getGmsToken {
                CoroutineScope(Dispatchers.Main).launch {
                    callback.invoke(it)
                }
            }
            HmsGmsUtil.isHmsAvailable(context) -> getHmsToken(context) {
                CoroutineScope(Dispatchers.Main).launch {
                    callback.invoke(it)
                }
            }
            else -> CoroutineScope(Dispatchers.Main).launch { callback.invoke("") }
        }
    }

    private fun getGmsToken(callback: (String) -> Unit) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->

            try {
                if (!task.isSuccessful) {
                    callback.invoke("")
                } else {
                    // Get new Instance ID accessToken
                    val fcmToken = task.result ?: ""
                    callback.invoke(fcmToken ?: "")
                }
            }catch (ex : Exception){
                ex.printStackTrace()
                callback.invoke("")
            }


        }

    }

    private fun getHmsToken(context: Context, callback: (String) -> Unit) {

        object : Thread() {
            override fun run() {
                try {
                    // Obtain the app ID from the agconnect-service.json file.
                    val appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id")

                    // Enter the token ID HCM.
                    val tokenScope = "HCM"
                    val token = HmsInstanceId.getInstance(context).getToken(appId, tokenScope)
                    // Check whether the token is empty.
                    callback(token ?: "")

                } catch (e: ApiException) {
                    e.printStackTrace()
                    callback("")
                }
            }
        }.start()
    }
}