package app.te.alo_chef.core.notifications.notification_manager

import app.te.alo_chef.core.notifications.app_notification_model.LimaRemoteMessages
import com.huawei.hms.push.RemoteMessage as HmsRemoteMessage
import com.google.firebase.messaging.RemoteMessage as GmsRemoteMessage

fun GmsRemoteMessage.toLimaRemoteMessage(): LimaRemoteMessages {
    return LimaRemoteMessages(
        data = data,
        title = data["title"],
        body = data["body"],
        sound = data["sound"]

    )
}


fun HmsRemoteMessage.toLimaRemoteMessage(): LimaRemoteMessages {
    return LimaRemoteMessages(
        data = this.dataOfMap,
        title = this.dataOfMap["title"],
        body = this.dataOfMap["body"],
        sound = this.dataOfMap["sound"]
    )
}
