package android.heeba.androidsample

import android.Manifest
import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.moengage.cards.core.MoECardHelper
import com.moengage.cards.core.listener.NewCardCountAvailableListener
import com.moengage.cards.core.listener.SyncCompleteListener
import com.moengage.cards.core.listener.UnClickedCountListener
import com.moengage.cards.core.model.Card
import com.moengage.cards.core.model.NewCardCountData
import com.moengage.cards.core.model.SyncCompleteData
import com.moengage.cards.core.model.UnClickedCountData
import com.moengage.cards.ui.CardActivity
import com.moengage.core.MoECoreHelper
import com.moengage.core.Properties
import com.moengage.core.analytics.MoEAnalyticsHelper
import com.moengage.core.model.GeoLocation
import com.moengage.inapp.MoEInAppHelper
import com.moengage.inapp.listeners.SelfHandledAvailableListener
import com.moengage.inapp.model.SelfHandledCampaignData
import com.moengage.inbox.core.MoEInboxHelper
import com.moengage.inbox.core.listener.OnMessagesAvailableListener
import com.moengage.inbox.core.model.InboxData
import com.moengage.inbox.ui.view.InboxActivity
import com.moengage.widgets.NudgeView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var nudge: NudgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nudge = findViewById(R.id.my_nudge)

        MoEAnalyticsHelper.trackDeviceLocale(applicationContext)
        val loginButton = findViewById<AppCompatButton>(R.id.login)
        loginButton.setOnClickListener() {
            MoEAnalyticsHelper.setUniqueId(applicationContext, "Heeba@gmail.com")
            MoEAnalyticsHelper.setFirstName(applicationContext, "Heeba")
            MoEAnalyticsHelper.setLastName(applicationContext, "Khan")
            MoEAnalyticsHelper.setUserName(applicationContext, "HeebaKhan")
            MoEAnalyticsHelper.setMobileNumber(applicationContext, "8077088706")
            MoEAnalyticsHelper.setUserAttribute(applicationContext, "userType", "freeUser")
        }

        val eventTrackButton = findViewById<AppCompatButton>(R.id.event_track)
        eventTrackButton.setOnClickListener() {
            val properties = Properties()
            properties
                // tracking integer
                .addAttribute("quantity", 2)
                // tracking string
                .addAttribute("product", "iPhone")
                // tracking date
                .addAttribute("purchaseDate", Date())
                // tracking double
                .addAttribute("price", 5999.99)
                // tracking location
                .addAttribute("userLocation", GeoLocation(40.77, 73.98))
                .setNonInteractive()
            MoEAnalyticsHelper.trackEvent(applicationContext, "Purchase", properties)

        }

        val defaultInboxButton = findViewById<AppCompatButton>(R.id.default_inbox)
        defaultInboxButton.setOnClickListener() {
            //launching the default InboxActivity
            val intent = Intent(this, InboxActivity::class.java)
            startActivity(intent)
        }

        val fetchMessageButton = findViewById<AppCompatButton>(R.id.fetch_messages)
        fetchMessageButton.setOnClickListener() {
            MoEInboxHelper.getInstance().fetchAllMessagesAsync(applicationContext, object :
                OnMessagesAvailableListener {
                override fun onMessagesAvailable(inboxData: InboxData?) {

                }
            })

        }

        val logOutButton = findViewById<AppCompatButton>(R.id.logOut)
        logOutButton.setOnClickListener() {
            MoECoreHelper.logoutUser(this)
        }


        val selfHandledInApp = findViewById<AppCompatButton>(R.id.selfHandledinApp)
        logOutButton.setOnClickListener() {
            MoEInAppHelper.getInstance()
                .getSelfHandledInApp(this@MainActivity, object : SelfHandledAvailableListener {
                    override fun onSelfHandledAvailable(data: SelfHandledCampaignData?) {
                        Log.d("self handled data", "self handled - ${data?.campaign}")
                    }
                })
            //   MoECoreHelper.logoutUser(this)
        }

        val openCards = findViewById<AppCompatButton>(R.id.cards)
        openCards.setOnClickListener() {
            val intent = Intent(this, CardActivity::class.java)
            startActivity(intent)

        }

        val cardCount = findViewById<AppCompatButton>(R.id.cardCount)
        cardCount.setOnClickListener() {
        MoECardHelper.getUnClickedCardCountAsync(this, object : UnClickedCountListener {
            override fun onCountAvailable(data: UnClickedCountData?) {
                Log.d("data unclicked count", data?.count.toString())
            }
        })
        MoECardHelper.getNewCardCountAsync(this, object : NewCardCountAvailableListener {
            override fun onCountAvailable(newCardCountData: NewCardCountData?) {
                Log.d("new card count", newCardCountData?.count.toString())
            }
        })
    }

        MoEInAppHelper.getInstance().getSelfHandledInApp(this, object : SelfHandledAvailableListener {
            override fun onSelfHandledAvailable(data: SelfHandledCampaignData?) {
                Log.d("BgTag", "${data?.campaign?.payload} self handled payload")
            }
        })

        MoECardHelper.setSyncCompleteListener(object : SyncCompleteListener {
            override fun onSyncComplete(data: SyncCompleteData?) {
                Log.d("sync listener", data.toString())
            }
        })

//Checking for permission


//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(
//                arrayOf(ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION),
//                100
//            )
//        }


    }

    override fun onStart() {
        super.onStart()
        nudge.initialiseNudgeView(this)

        // MoEInAppHelper.getInstance().showInApp(applicationContext)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            grantResults[0] = PackageManager.PERMISSION_GRANTED
            grantResults[1] = PackageManager.PERMISSION_GRANTED
        }
    }
}

