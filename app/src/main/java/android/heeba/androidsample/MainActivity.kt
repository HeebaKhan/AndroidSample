package android.heeba.androidsample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.moengage.core.analytics.MoEAnalyticsHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginButton = findViewById<AppCompatButton>(R.id.login)
        loginButton.setOnClickListener() {
            MoEAnalyticsHelper.setUniqueId(applicationContext, "Heeba@gmail.com")
        }
    }
}