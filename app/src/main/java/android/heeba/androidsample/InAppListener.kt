package android.heeba.androidsample

import android.util.Log
import com.moengage.inapp.MoEInAppHelper
import com.moengage.inapp.listeners.InAppLifeCycleListener
import com.moengage.inapp.model.InAppData

class InAppListener: InAppLifeCycleListener {

    override fun onDismiss(inAppData: InAppData) {
        Log.d("InAppListener","In App Dismissed", )
    }

    override fun onShown(inAppData: InAppData) {
        Log.d("InAppListener","In App Shown")


    }
}