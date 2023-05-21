package com.example.tripreview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.assignment_review.DatabaseConnector
import com.google.android.material.bottomnavigation.BottomNavigationView

class userdetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetails)
        val editTextText = findViewById<EditText>(R.id.editfirstname)
        val editTextText2 = findViewById<EditText>(R.id.editlastname)
        val database = DatabaseConnector(this)
        val userdata = database.getUserById()
        editTextText.setText(userdata[0].toString())
        editTextText2.setText(userdata[1].toString())

        val upbutton = findViewById<Button>(R.id.updateButton)
        upbutton.setOnClickListener {
            val firstName = editTextText.text.toString()
            val lastName = editTextText2.text.toString()
            val updatedUser = database.updateUser(firstName, lastName)
            Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation);
        navigation.selectedItemId = R.id.ic_account
        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    val intent = Intent(this, item_review::class.java)
                    startActivity(intent)
                }
                R.id.ic_myreview -> {
                    val intent = Intent(this, Review::class.java)
                    startActivity(intent)
                }
                R.id.ic_account -> {
                    val intent = Intent(this, userdetails::class.java)
                    startActivity(intent)
                }
                R.id.ic_logout -> {
                    val sharedPreferences = this.getSharedPreferences("tripadvisor", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()

                }
            }
            true
        }

        }

    }

