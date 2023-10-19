package com.example.assignment_review

import android.content.Context
import com.example.tripreview.ReviewList
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Connection
import java.sql.RowId

class DatabaseConnector(context:Context) {

    val url = "jdbc:mysql://sql.freedb.tech:3306/freedb_tripadvisor?autoReconnect=true&useSSL=false"
    val user = "freedb_tripadvisor"
    val password = "dX3?gudJ7nHQypS"
    val sharedPreferences = context.getSharedPreferences("tripadvisor", Context.MODE_PRIVATE)
    private var connection: Connection? = null
    val context=context

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
            "select * from users where userName='$username' and password='$password'";
        println(authenticateQuery)
        val thread = Thread {
            try {
                println(authenticateQuery)
                val statement = connection?.createStatement()
                val resultSet = statement?.executeQuery(authenticateQuery);
                val sharedPreferences = context.getSharedPreferences("tripadvisor", Context.MODE_PRIVATE)
                while (resultSet?.next() == true) {
                    userExist = true
                    val editor = sharedPreferences.edit()
                    editor.remove("username")
                    editor.putString("username", resultSet.getString("username"))
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
                val query =
                    "INSERT INTO reviews (username, review) VALUES ('$username', '$review')"
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

    fun deleteReview(Id: Int) {
        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
                val statement = connection?.createStatement()
                val query = "DELETE FROM reviews WHERE reviewID = '$Id'"
                val rowsAffected = statement?.executeUpdate(query)

                if (rowsAffected != null && rowsAffected > 0) {
                    println("Review deleted successfully")
                } else {
                    println("Failed to delete review")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.close()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    data class User(var firstName: String, var lastName: String)

    fun getUserById(): MutableList<String> {
        var firstName = ""
        var lastName = ""
        val sharedPreferences = context.getSharedPreferences("tripadvisor", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "default value")
        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
                val statement = connection?.createStatement()
                val query = "SELECT firstName, lastName FROM users WHERE username = '$username'"
                println(query)
                val resultSet = statement?.executeQuery(query)

                if (resultSet != null && resultSet.next()) {
                    firstName = resultSet.getString("firstName")
                    lastName = resultSet.getString("lastName")


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
        return mutableListOf(firstName, lastName)
    }

    fun updateUser(firstName: String, lastName: String) {
        val sharedPreferences = context.getSharedPreferences("tripadvisor", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "default value")
        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
                val statement = connection?.createStatement()
                val query = "UPDATE users SET firstName = '${firstName}', lastName = '${lastName}' WHERE username = '$username'"
                println(query)
                statement?.executeUpdate(query)
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

    fun selectReviews(): List<ReviewList> {
        val reviewList = mutableListOf<ReviewList>()

        val thread = Thread {
            try {
                val statement = connection?.createStatement()
                val query = "SELECT username, review,reviewId FROM reviews"
                val resultSet = statement?.executeQuery(query)

                while (resultSet?.next() == true) {
                    val username = resultSet.getString("username")
                    val review = resultSet.getString("review")
                    val reviewId = resultSet.getInt("reviewId")

                    val reviewItem = ReviewList(username = username, review = review, reviewId=reviewId)
                    reviewList.add(reviewItem)
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

        return reviewList
    }

    fun updateReview(reviewId: Int, review: String) {
        val thread = Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, user, password)
                val statement = connection?.createStatement()
                val reviewID= reviewId
                val query = "UPDATE reviews SET review = '$review' WHERE reviewID = $reviewID"
                val rowsAffected = statement?.executeUpdate(query)

                if (rowsAffected != null && rowsAffected > 0) {
                    println("Review updated successfully")
                } else {
                    println("Failed to update review")
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



