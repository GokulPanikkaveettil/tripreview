package com.example.tripreview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signup = findViewById<Button>(R.id.signup);
        signup.setOnClickListener{
            val intent = Intent(this, userdetails::class.java)
            startActivity(intent)
        }
        val login = findViewById<Button>(R.id.startlogin);
        login.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}