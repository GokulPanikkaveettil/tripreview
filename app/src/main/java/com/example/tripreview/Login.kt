package com.example.tripreview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.assignment_review.DatabaseConnector

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.login);
        val  username = findViewById<EditText>(R.id.username);
        val password = findViewById<EditText>(R.id.password);

        login.setOnClickListener{
            val database=DatabaseConnector(this)
            database.login(username.text.toString(),password.text.toString());
            val userexist=database.login(username.text.toString(),password.text.toString());

            if (username.text.toString().length <= 0) {
                Toast.makeText(this@Login, "UserName cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if (password.text.toString().length <= 0) {
                Toast.makeText(this@Login, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(
                userexist == true
            )
            {
                val intent = Intent(this, Review::class.java)
                startActivity(intent)
            }
            else if (
                userexist == false
            )
            {
                Toast.makeText(this@Login,"Invalid UserName or Password", Toast.LENGTH_SHORT).show()
            }
            }
        }
    }