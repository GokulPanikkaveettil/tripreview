package com.example.assignment_review

import android.content.Context
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Connection

class DatabaseConnector(context:Context) {

    val url = "jdbc:mysql://sql.freedb.tech:3306/freedb_tripadvisor?autoReconnect=true&useSSL=false"
    val user = "freedb_tripadvisor"
    val password = "PcDfB\$3Et%p%jq3"
    val sharedPreferences = context.getSharedPreferences("TripAdvisor", Context.MODE_PRIVATE)
    private var connection: Connection? = null

    init {
        connect()
    }

    fun connect() {

        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
            } catch (e: SQLException) {
                println("inside catch")
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                println("class not found")
                e.printStackTrace()
            }
        }

        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun signup(firstName: String, lastName: String, userName: String, pass: String) {
        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
                val statement = connection?.createStatement()
                val query =
                    "insert into users (firstName,lastName,userName,password) values ('$firstName','$lastName','$userName', '$pass')"
                val resultSet = statement?.executeUpdate(query);

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



        fun login(username: String, password: String): Boolean {
            println("$username $password")
            var userExist = false
            val authenticateQuery =
                "select * from users where username='$username' and password='$password'";
            val thread = Thread {
                try {
                    println(authenticateQuery)
                    val statement = connection?.createStatement()
                    val resultSet = statement?.executeQuery(authenticateQuery);
                    while (resultSet?.next() == true) {
                        userExist = true
                        val editor = sharedPreferences.edit()
                        editor.putString("username",resultSet.getString("username"))
                        editor.apply()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            thread.start()
            try {
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return userExist

        }
    fun insertReview(userId: Int, username: String, review: String) {
        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
                val statement = connection?.createStatement()
                val query = "INSERT INTO reviews (userid, username, review) VALUES ('$userId', '$username', '$review')"
                val rowsAffected = statement?.executeUpdate(query)

                if (rowsAffected != null && rowsAffected > 0) {

                    println("Review inserted successfully")
                } else {

                    println("Failed to insert review")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
