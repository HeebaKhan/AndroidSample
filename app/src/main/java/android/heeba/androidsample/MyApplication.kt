package android.heeba.androidsample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.moengage.core.*
import com.moengage.core.analytics.MoEAnalyticsHelper
import com.moengage.core.config.*
import com.moengage.core.listeners.AppBackgroundListener
import com.moengage.core.model.AppBackgroundData
import com.moengage.core.model.AppStatus
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.firebase.listener.NonMoEngagePushListener
import com.moengage.inapp.MoEInAppHelper
import com.moengage.pushbase.MoEPushHelper
import com.moengage.pushbase.listener.TokenAvailableListener
import com.moengage.pushbase.model.Token

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("HEEBA TAG", "This is the world.")
        val moEngage = MoEngage.Builder(this, "OOXNXYDEZL929BZJU6WLVO8Q")
            .setDataCenter(DataCenter.DATA_CENTER_3)
            .configureLogs(LogConfig(LogLevel.VERBOSE, isEnabledForReleaseBuild = true))
            .configureFcm(FcmConfig(false))
            .configureGeofence(GeofenceConfig(true))
            .configureNotificationMetaData(
                NotificationConfig(
                    R.drawable.ic_stat_name,
                    R.drawable.download
                )
            )
            .configureTrackingOptOut(
                TrackingOptOutConfig(
                    isCarrierTrackingEnabled = true,
                    isDeviceAttributeTrackingEnabled = true,
                    optOutActivities = null
                )
            ).configureInApps(InAppConfig(optOutActivities = null))
            .build()



        MoEngage.initialiseDefaultInstance(moEngage)
        MoEInAppHelper.getInstance().addInAppLifeCycleListener(InAppListener())
        MoEPushHelper.getInstance().registerMessageListener(CustomPushListener())

        MoEFireBaseHelper.getInstance().addTokenListener(object : TokenAvailableListener {
            override fun onTokenAvailable(token: Token) {
                Log.d("token", "token value is $token")
            }
        })
        MoEFireBaseHelper.getInstance().addNonMoEngagePushListener(object: NonMoEngagePushListener {
            override fun onPushReceived(remoteMessage: RemoteMessage) {

            }
        } )
        MoEAnalyticsHelper.setAppStatus(this, AppStatus.INSTALL)

        enableAdIdTracking(this)
        MoECoreHelper.addAppBackgroundListener(object : AppBackgroundListener {
            override fun onAppBackground(context: Context, data: AppBackgroundData) {
                Log.d("BgTag", "App is in background")
            }

        })

        createChannel()

    }

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "New Channel"
            val descriptionText = "Audio Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel("audio_channel", name, importance)
            mChannel.description = descriptionText
            mChannel.enableVibration(true)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            mChannel.setSound(
                Uri.parse("android.resource://${packageName}/" + R.raw.car_alarm),
                attributes
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}