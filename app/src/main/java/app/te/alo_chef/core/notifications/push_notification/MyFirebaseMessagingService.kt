package app.te.alo_chef.core.notifications.push_notification

import android.util.Log
import app.te.alo_chef.core.notifications.handler.NotificationHandler
import app.te.alo_chef.core.notifications.notification_manager.toLimaRemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :
    FirebaseMessagingService() {
    lateinit var notificationHandler: NotificationHandler
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("onMessageReceived", "onMessageReceived: "+remoteMessage.notification?.title)
        notificationHandler = NotificationHandler(this)
        notificationHandler.handleNotifications(remoteMessage.toLimaRemoteMessage())
    }

    override fun onNewToken(token: String) {
        Log.e("onMessageReceived", "onNewToken: "+token)
    }

}