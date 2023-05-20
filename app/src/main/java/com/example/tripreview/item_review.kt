package com.example.tripreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class ReviewList(val username: String, val review: String)

class item_review : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_list_view)

        val recyclerView: RecyclerView = findViewById(R.id.recycler)

        // Create a list of reviews (replace with your actual data)
        val reviewList = mutableListOf<ReviewList>().apply {
            add(ReviewList(username = "John", review = "Great experience!"))
            add(ReviewList(username = "Mary", review = "Excellent service!"))
            add(ReviewList(username = "David", review = "Amazing place!"))
        }
        // Create an instance of the ReviewAdapter
        val reviewAdapter = ReviewAdapter(reviewList)

        // Set the adapter on the RecyclerView
        recyclerView.adapter = reviewAdapter

        // Set the layout manager for the RecyclerView (e.g., LinearLayoutManager)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Define references to views in item_review.xml
    val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
    val reviewTextView: TextView = itemView.findViewById(R.id.reviewTextView)
}

class ReviewAdapter(private val reviews: List<ReviewList>) : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        // Bind review data to views in the ViewHolder
        holder.usernameTextView.text = review.username
        holder.reviewTextView.text = review.review
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}