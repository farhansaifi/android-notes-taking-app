@file:Suppress("UNREACHABLE_CODE")

package com.example.todonotesapp.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.data.local.pref.PrefConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.login.LoginActivity
import com.example.todonotesapp.mynotes.MyNotesActivity
import com.example.todonotesapp.onboarding.OnBoardingActivity
import com.example.todonotesapp.data.local.pref.StoreSession
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity :AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var handler: Handler
    lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // creating a function for sharedPreference in this activity
        setupSharedPreference()

        // creating function for delay 2 second to run next function
        goToNext()

        // creating function for firebase cloud messaging (FCM)
        getFCMToken()
    }

    private fun goToNext() {
        handler = Handler()
        runnable = Runnable {
            // Creating a function for check login status
            checkLoginStatus()
        }
        handler.postDelayed(runnable,900)
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
            /*Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()*/
        })
    }

    private fun setupSharedPreference() {

       /* sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)*/
        StoreSession.init(this)
    }

    private fun checkLoginStatus() {

        // if user is logged in take them to MyNotesActivity
        // else show them loginActivity
        // Check these conditions we need SharedPreferences
            /*val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN,false)
            val isBoardingSuccess = sharedPreferences.getBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY,false)*/
        val isLoggedIn = StoreSession.read(PrefConstant.IS_LOGGED_IN)
        val isBoardingSuccess = StoreSession.read(PrefConstant.ON_BOARDED_SUCCESSFULLY)

       /* Toast.makeText(this@SplashActivity,isLoggedIn.toString(),Toast.LENGTH_LONG).show()*/

        if (isLoggedIn!!){

            // Check Condition if logged_in is TRUE open MyNoteActivity
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)

        } else {
            // check onBoarding condition if onBoarding is false show tutorial screen
            // else go to loginActivity
                if (isBoardingSuccess!!) {

                    // Check Condition if logged_in is FALSE open LoginActivity
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)

                } else {
                    val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                    startActivity(intent)
                }
        }
        // Terminate the SplashActivity
        finish()
    }
    // Remove the handler instance when we press back
    override fun onBackPressed() {
        super.onBackPressed()
        handler.removeCallbacks(runnable)
    }
}