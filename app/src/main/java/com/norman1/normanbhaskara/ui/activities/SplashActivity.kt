package com.norman1.normanbhaskara.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)

        try {
            Timer().schedule(500) {
                startActivity(intent)
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}