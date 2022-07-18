package android.heeba.androidsample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.moengage.core.Properties
import com.moengage.core.analytics.MoEAnalyticsHelper
import com.moengage.core.model.GeoLocation
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MoEAnalyticsHelper.trackDeviceLocale(applicationContext)
        val loginButton = findViewById<AppCompatButton>(R.id.login)
        loginButton.setOnClickListener() {
            MoEAnalyticsHelper.setUniqueId(applicationContext, "Heeba@gmail.com")
            MoEAnalyticsHelper.setFirstName(applicationContext,"Heeba")
            MoEAnalyticsHelper.setLastName(applicationContext,"Khan")
            MoEAnalyticsHelper.setUserName(applicationContext,"HeebaKhan")
            MoEAnalyticsHelper.setMobileNumber(applicationContext,"8077088706")
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
    }
}