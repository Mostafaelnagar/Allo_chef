package app.te.alo_chef.core.notifications.notification_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import app.te.alo_chef.R
import app.te.alo_chef.core.notifications.app_notification_model.LimaRemoteMessages
import app.te.alo_chef.core.notifications.app_notification_model.NotificationsType
import app.te.alo_chef.presentation.home.HomeActivity


fun showNotification(context: Context, remoteMessage: LimaRemoteMessages) {
    val channelId = "channelIds"
    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentTitle(remoteMessage.title)
        .setContentText(remoteMessage.body)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(remoteMessage.body)
        )
        .setAutoCancel(true)
        .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
        .setSound(SoundUtils.getNotificationsSound())
        .setContentIntent(createNotificationIntent(context, remoteMessage.data))

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    // Since android Oreo notification channel is needed.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_HIGH
        )
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.setSound(SoundUtils.getNotificationsSound(), audioAttributes)
        notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(
        System.currentTimeMillis().toInt() /* ID of notification */,
        notificationBuilder.build()
    )

}


private fun createNotificationIntent(
    context: Context,
    data: MutableMap<String, String>
): PendingIntent {
    if (data[NotificationsType.ORDER_DETAILS.notificationsType]?.isNotEmpty() == true)
        return createOrderDetailsIntent(
            context,
            data[NotificationsType.ORDER_DETAILS.notificationsType]
        )
    return createDefaultNotificationIntent(context)
}

fun createOrderDetailsIntent(context: Context, orderId: String?): PendingIntent {
    val bundle = Bundle()
    bundle.putInt(NotificationsType.ORDER_DETAILS.notificationsType, orderId?.toInt() ?: 0)
    return NavDeepLinkBuilder(context)
        .setComponentName(HomeActivity::class.java)
        .setGraph(R.navigation.nav_home)
        .setDestination(R.id.trackOrderFragment)
        .setArguments(bundle)
        .createPendingIntent()
}

private fun createDefaultNotificationIntent(
    context: Context,
): PendingIntent {
    return NavDeepLinkBuilder(context)
        .setComponentName(HomeActivity::class.java)
        .setGraph(R.navigation.nav_home)
        .setDestination(R.id.home_fragment)
        .createPendingIntent()

}

private fun getPendingIntentFlag(): Int {
    return when {
        Build.VERSION.SDK_INT >= 31 -> PendingIntent.FLAG_ONE_SHOT
        else -> PendingIntent.FLAG_CANCEL_CURRENT
    }
}
