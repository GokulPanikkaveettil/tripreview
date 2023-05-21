package com.example.tripreview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_review.DatabaseConnector
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.graphics.Rect



data class ReviewList(val username: String, val review: String)

class item_review : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_list_view)

        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        val database = DatabaseConnector(this)
        val reviewList = database.selectReviews()
        val reviewAdapter = ReviewAdapter(reviewList)
        recyclerView.adapter = reviewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation);
        navigation.selectedItemId = R.id.ic_home
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


class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
    val reviewTextView: TextView = itemView.findViewById(R.id.reviewTextView)
}

class ReviewAdapter(private val reviews: List<ReviewList>) : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]


        holder.usernameTextView.text = review.username
        holder.reviewTextView.text = review.review
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}