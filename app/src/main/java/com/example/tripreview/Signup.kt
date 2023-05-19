package com.example.tripreview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.assignment_review.DatabaseConnector

class Signup : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signup = findViewById<Button>(R.id.signup);
        val firstName = findViewById<EditText>(R.id.editTextfirstName);
        val lastName = findViewById<EditText>(R.id.editTextlastName);
        val userName = findViewById<EditText>(R.id.editTextuserName);
        val password = findViewById<EditText>(R.id.editTextpassword);

        signup.setOnClickListener{
            if (firstName.text.toString().length <= 0) {
                Toast.makeText(this@Signup, "First Name cannot be empty", Toast.LENGTH_SHORT).show()

            }
            else if (lastName.text.toString().length <= 0) {
                Toast.makeText(this@Signup, "Last Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if (userName.text.toString().length <= 0) {
                Toast.makeText(this@Signup, "UserName cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if (password.text.toString().length <= 0) {
                Toast.makeText(this@Signup, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else    {
                val db = DatabaseConnector(this)
                db.signup(firstName.text.toString(), lastName.text.toString(), userName.text.toString(), password.text.toString())
                password.text.clear()
            }
        }


    }
}