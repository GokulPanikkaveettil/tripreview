package com.example.tripreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.assignment_review.DatabaseConnector

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
    }






   //editTextText.setText(userDetails.firstName)
   // editTextText2.setText(userDetails.lastName)


//     Update button click event
//    val updateButton = findViewById<Button>(R.id.button)
//    updateButton.setOnClickListener {
//        val editedFirstName = editTextText.text.toString()
//        val editedLastName = editTextText2.text.toString()

        // Update the user details in the database
//        val success = database.updateUserDetails(userId, editedFirstName, editedLastName)
//
//        if (success) {
//            Toast.makeText(this, "User details updated successfully", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Failed to update user details", Toast.LENGTH_SHORT).show()
//        }
    }
