package android.heeba.androidsample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.moengage.pushbase.push.PushMessageListener

class CustomPushListener: PushMessageListener() {

    override fun onNotificationClick(activity: Activity, payload: Bundle) {
      //  super.onNotificationClick(activity, payload)
        Log.e("Custom Listener", "SDK will not do anything in this case")
        if (payload.containsKey("key")) {
            Log.e("Custom Listener", payload.getString("key")!!)

        } else {
            super.onNotificationClick(activity, payload)
        }
    }

    override fun handleCustomAction(context: Context, payload: String) {

     super.handleCustomAction(context, payload)

    }

}