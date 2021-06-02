@file:Suppress("UNREACHABLE_CODE")

package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R

class SplashActivity :AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // creating a function for sharedPreference in this activity
        setupSharedPreference()

        // Creating a function for check login status
        checkLoginStatus()
    }

    private fun setupSharedPreference() {

        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun checkLoginStatus() {

        // if user is logged in take them to MyNotesActivity
        // else show them loginActivity
        // Check these conditions we need SharedPreferences
        val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN,false)

       /* Toast.makeText(this@SplashActivity,isLoggedIn.toString(),Toast.LENGTH_LONG).show()*/

        if (isLoggedIn){

            // Check Condition if logged_in is TRUE open MyNoteActivity
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
            // Terminate the SplashActivity
            finish()

        } else {

            // Check Condition if logged_in is FALSE open LoginActivity
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            // Terminate the SplashActivity
            finish()
        }
    }

}