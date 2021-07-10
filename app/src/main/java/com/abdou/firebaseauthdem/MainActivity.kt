package com.abdou.firebaseauthdem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var tvUserID: TextView
    lateinit var tvEmailID: TextView
    lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvUserID = findViewById(R.id.tvUserID)
        tvEmailID = findViewById(R.id.tvEmailID)
        btnLogout = findViewById(R.id.btnLogout)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        tvUserID.text = "User ID :: $userId"
        tvEmailID.text = "User ID :: $emailId"

        btnLogout.setOnClickListener{
            // Logout from app
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

    }
}