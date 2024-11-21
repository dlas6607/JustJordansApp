package com.example.justjordansapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

<uses-permission android:name="android.permission.INTERNET" />

class payment_buttons : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find ImageButtons by their IDs
        val imageButton1: ImageButton = findViewById(R.id.imageButton1)
        val imageButton2: ImageButton = findViewById(R.id.imageButton2)
        val imageButton3: ImageButton = findViewById(R.id.imageButton3)

        // Set click listeners
        imageButton1.setOnClickListener {
            openUrl("https://cash.app/login")
        }

        imageButton2.setOnClickListener {
            openUrl("https://id.venmo.com/signin?country.x=US&locale.x=en&ctxId=AAH0usqTxc1exaFvUOP8orY9A03t6lZo3ZzTGVt0Z_9iV_LghbD20wLDjT8k4e3Mx1D4qvOB8zYmBfK-Z5U42i4=#/lgn")
        }

        imageButton3.setOnClickListener {
            openUrl("https://www.zellepay.com/")
        }
    }

    // Function to open a URL
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
