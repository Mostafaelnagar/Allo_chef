package app.te.alo_chef.core.notifications.handler

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import app.te.alo_chef.R
import app.te.alo_chef.core.notifications.app_notification_model.LimaRemoteMessages
import app.te.alo_chef.core.notifications.notification_manager.SoundUtils
import app.te.alo_chef.core.notifications.notification_manager.showNotification
import app.te.alo_chef.presentation.base.utils.Constants
import app.te.alo_chef.presentation.home.HomeActivity
import kotlin.random.Random

class NotificationHandler(private val context: Context) {

    fun handleNotifications(remoteMessages: LimaRemoteMessages) {
        SoundUtils.uriString = "android.resource://" + context.packageName + "/"
//        SoundUtils.playNotificationSound(context)
        showNotification(context,remoteMessages)
    }

    fun sendNotification(messageBody: LimaRemoteMessages) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra(Constants.NOTIFICATION, true)
        // Set the Activity to start in a new, empty task
        val pendingIntent = PendingIntent.getActivity(
            context,
            Random.nextInt(),
            createNotificationIntent(),
            getPendingIntentFlag()
        )

//        val pendingIntent = NavDeepLinkBuilder(context)
//            .setComponentName(HomeActivity::class.java)
//            .setGraph(R.navigation.nav_home)
//            .setDestination(R.id.home_fragment)
//            .setArguments(bundle)
//            .createPendingIntent()
        val channelId = "channelId"

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(messageBody.sound)
            .setContentText(messageBody.body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(messageBody.body)
            )
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setSound(SoundUtils.getNotificationsSound())
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)

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
                .setUsage(AudioAttributes.USAGE_ALARM)
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

    private fun createNotificationIntent(): Intent {
        return createDefaultNotificationIntent(context)
    }

    private fun createDefaultNotificationIntent(
        context: Context,
    ): Intent {
        return Intent(context, HomeActivity::class.java).apply {
            try {
                putExtra(Constants.NOTIFICATION, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getPendingIntentFlag(): Int {
        return when {
            Build.VERSION.SDK_INT >= 31 -> PendingIntent.FLAG_IMMUTABLE
            else -> PendingIntent.FLAG_CANCEL_CURRENT
        }
    }
}