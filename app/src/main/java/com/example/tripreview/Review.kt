package com.example.tripreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.assignment_review.DatabaseConnector
import com.google.android.material.bottomnavigation.BottomNavigationView

class Review : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)


        val submitreview = findViewById<Button>(R.id.submitreview)
        val review = findViewById<EditText>(R.id.review)
        val reviewEditText = findViewById<EditText>(R.id.review)
        val sharedPreferences = getSharedPreferences("TripAdvisor", Context.MODE_PRIVATE)
        submitreview.setOnClickListener {
            val database = DatabaseConnector(this)
            val username = sharedPreferences.getString("username", "default value")
            database.insertReview(1, username.toString(), review.text.toString())
            review.text.clear()
        }
        val deleteReviewButton = findViewById<Button>(R.id.deletereviewbutton)
        val database = DatabaseConnector(this)
        deleteReviewButton.setOnClickListener {
            val username = sharedPreferences.getString("username", "default value")
            val reviewText = reviewEditText.text.toString()
            database.deleteReview(18)
            reviewEditText.text.clear()
        }
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation);
        navigation.selectedItemId = R.id.ic_myreview
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


