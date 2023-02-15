package app.te.alo_chef.core.notifications.push_notification

import android.util.Log
import app.te.alo_chef.core.notifications.handler.NotificationHandler
import app.te.alo_chef.core.notifications.notification_manager.toLimaRemoteMessage
import com.huawei.hms.push.RemoteMessage
import com.huawei.hms.push.HmsMessageService

class HCMService : HmsMessageService() {

    lateinit var notificationHandler: NotificationHandler
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("onMessageReceived", "onMessageReceived: Huwawi")
        notificationHandler = NotificationHandler(this)
        notificationHandler.handleNotifications(remoteMessage.toLimaRemoteMessage())
    }

    override fun onNewToken(token: String) {
    }

}