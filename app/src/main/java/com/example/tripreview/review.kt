package com.example.tripreview

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.assignment_review.DatabaseConnector

class review : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val submitreview = findViewById<Button>(R.id.submitreview);
        val  review = findViewById<EditText>(R.id.review)
        val sharedPreferences = getSharedPreferences("TripAdvisor", Context.MODE_PRIVATE)
        submitreview.setOnClickListener{
            val database= DatabaseConnector(this)
            val username = sharedPreferences.getString("username", "default value")
            database.insertReview(1, username.toString(),review.text.toString())
            review.text.clear()
        }
    }
}