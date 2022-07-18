package android.heeba.androidsample

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.pushbase.MoEPushHelper

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (MoEPushHelper.getInstance().isFromMoEngagePlatform(message.data)){
            MoEFireBaseHelper.getInstance().passPushPayload(applicationContext, message.data)
        }else{
            // your app's business logic to show notification
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        MoEFireBaseHelper.getInstance().passPushToken(applicationContext,token)
    }

}