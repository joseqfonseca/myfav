package com.joseqfonseca.myfav.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.joseqfonseca.myfav.R
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        thread(true) {
            Thread.sleep(0)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}