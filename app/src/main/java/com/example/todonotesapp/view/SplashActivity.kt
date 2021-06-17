@file:Suppress("UNREACHABLE_CODE")

package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.onboarding.OnBoardingActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity :AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // creating a function for sharedPreference in this activity
        setupSharedPreference()

        // Creating a function for check login status
        checkLoginStatus()

        // creating function for firebase cloud messaging (FCM)
        getFCMToken()
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("SplashActivity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result.toString()

            // Log and toast

            Log.d("SplashActivity", token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupSharedPreference() {

        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun checkLoginStatus() {

        // if user is logged in take them to MyNotesActivity
        // else show them loginActivity
        // Check these conditions we need SharedPreferences
        val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN,false)
        val isBoardingSuccess = sharedPreferences.getBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY,false)

       /* Toast.makeText(this@SplashActivity,isLoggedIn.toString(),Toast.LENGTH_LONG).show()*/

        if (isLoggedIn){

            // Check Condition if logged_in is TRUE open MyNoteActivity
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
            // Terminate the SplashActivity
            finish()

        } else {
            // check onBoarding condition if onBoarding is false show tutorial screen
            // else go to loginActivity
                if (isBoardingSuccess) {

                    // Check Condition if logged_in is FALSE open LoginActivity
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    // Terminate the SplashActivity
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                    startActivity(intent)
                }
        }
    }

}