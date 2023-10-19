package com.example.tripreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.assignment_review.DatabaseConnector
import com.google.android.material.bottomnavigation.BottomNavigationView

class Review : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)


        val submitreview = findViewById<Button>(R.id.submitreview)
        val database = DatabaseConnector(this)
        val review = findViewById<EditText>(R.id.review)
        val reviewEditText = findViewById<EditText>(R.id.review)
        reviewEditText.setText(this.intent.getStringExtra("review").toString())
        val username = this.intent.getStringExtra("username").toString()
        val sharedPreferences = getSharedPreferences("tripadvisor", Context.MODE_PRIVATE)
        submitreview.setOnClickListener {

            val username = sharedPreferences.getString("username", "default value")
            database.insertReview(1, username.toString(), review.text.toString())
            review.text.clear()
            val intent = Intent(this, item_review::class.java)
            startActivity(intent)
        }
        val updateReviewButton = findViewById<Button>(R.id.Editreviewbutton)
        val reviewupdateText = findViewById<EditText>(R.id.review)

        updateReviewButton.setOnClickListener {
            val username = sharedPreferences.getString("username", "default value")
            val reviewId = this@Review.intent.getStringExtra("reviewId").toString()
            val reviewText = reviewEditText.text.toString()
            Toast.makeText(this, reviewId.toString(), Toast.LENGTH_SHORT).show()

            if (reviewId.toInt() != -1) {
                database.updateReview(reviewId.toInt(), reviewText)
                reviewEditText.text.clear()
                Toast.makeText(this, "Review updated successfully", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Failed to update review", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, item_review::class.java)
            startActivity(intent)
        }


            val deleteReviewButton = findViewById<Button>(R.id.deletereviewbutton)
            deleteReviewButton.setOnClickListener {
                val username = sharedPreferences.getString("userName", "default value")
                val reviewId = this@Review.intent.getStringExtra("reviewId").toString()
                val reviewText = reviewEditText.text.toString()
                database.deleteReview(reviewId.toInt())
                reviewEditText.text.clear()
                Toast.makeText(this, "Review deleted successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, item_review::class.java)
                startActivity(intent)
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
                        val sharedPreferences =
                            this.getSharedPreferences("tripadvisor", Context.MODE_PRIVATE)
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

