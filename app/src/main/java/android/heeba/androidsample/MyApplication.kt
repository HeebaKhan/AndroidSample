package android.heeba.androidsample

import android.app.Application
import android.content.Context
import android.util.Log
import com.moengage.core.DataCenter
import com.moengage.core.LogLevel
import com.moengage.core.MoEngage
import com.moengage.core.analytics.MoEAnalyticsHelper
import com.moengage.core.config.FcmConfig
import com.moengage.core.config.LogConfig
import com.moengage.core.enableAdIdTracking
import com.moengage.core.model.AppStatus

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("HEEBA TAG", "This is the world.")
        val moEngage = MoEngage.Builder(this, "OOXNXYDEZL929BZJU6WLVO8Q")
            .setDataCenter(DataCenter.DATA_CENTER_3)
            .configureLogs(LogConfig(LogLevel.VERBOSE))
            .configureFcm(FcmConfig(false))
            .enableEncryption()
            .build()


        MoEngage.initialiseDefaultInstance(moEngage)
        MoEAnalyticsHelper.setAppStatus(this, AppStatus.INSTALL)

        enableAdIdTracking(this)


    }
}