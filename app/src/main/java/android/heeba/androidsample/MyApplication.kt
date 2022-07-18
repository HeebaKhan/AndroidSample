package android.heeba.androidsample

import android.app.Application
import android.content.Context
import android.util.Log
import com.moengage.core.*
import com.moengage.core.analytics.MoEAnalyticsHelper
import com.moengage.core.config.FcmConfig
import com.moengage.core.config.LogConfig
import com.moengage.core.config.NotificationConfig
import com.moengage.core.config.TrackingOptOutConfig
import com.moengage.core.listeners.AppBackgroundListener
import com.moengage.core.model.AppBackgroundData
import com.moengage.core.model.AppStatus

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("HEEBA TAG", "This is the world.")
        val moEngage = MoEngage.Builder(this, "OOXNXYDEZL929BZJU6WLVO8Q")
            .setDataCenter(DataCenter.DATA_CENTER_3)
            .configureLogs(LogConfig(LogLevel.VERBOSE))
            .configureFcm(FcmConfig(false))
            .configureNotificationMetaData(NotificationConfig(R.drawable.ic_stat_name, R.drawable.download))
            .configureTrackingOptOut(TrackingOptOutConfig(isCarrierTrackingEnabled = true, isDeviceAttributeTrackingEnabled = true, optOutActivities = null ))
            .build()



        MoEngage.initialiseDefaultInstance(moEngage)
        MoEAnalyticsHelper.setAppStatus(this, AppStatus.INSTALL)

        enableAdIdTracking(this)
MoECoreHelper.addAppBackgroundListener(object: AppBackgroundListener{
    override fun onAppBackground(context: Context, data: AppBackgroundData) {
        Log.d("BgTag", "App is in background")
    }

})

    }
}