package com.example.todonotesapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    // Create some variables for editText or button type for editText or button
    lateinit var editTextFullName: EditText
    lateinit var editTextUserName : EditText
    lateinit var buttonLogin : Button
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor :SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)

        //binding the views function
        bindViews();

        //Let's create a function for  sharedPreferences
        setUpSharedPreferences()
    }

    private fun bindViews() {

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)

        val clickAction = object : View.OnClickListener{
            override fun onClick(v: View?) {
               val fullName = editTextFullName.text.toString()
               val userName = editTextUserName.text.toString()

                // Put a condition to check String variable are empty or fill
                // fullName or userName should not be empty
                if (fullName.isNotEmpty() && userName.isNotEmpty()){

                    // Here we code onClick event in object
                    // Goes this activity to myNotesActivity using intent
                    val intent = Intent(this@LoginActivity,MyNotesActivity::class.java)

                    // we send some data from this activity to another activity using putExtra
                    // putExtra is part of intent
                    intent.putExtra(AppConstant.FULL_NAME,fullName)
                    startActivity(intent)

                    // save full name myNoteActivity function
                    saveFullName(fullName);

                    // After login saved shared preference for login status function
                    saveLoginStatus();
                }
            }

        }
        buttonLogin = findViewById(R.id.buttonLogin)
    }

    private fun setUpSharedPreferences() {

        // SharedPreferences editor is read write or delete SharedPreferences
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)

    }

    private fun saveFullName(fullName: String) {

        editor = sharedPreferences.edit()
        editor.putString(PrefConstant.FULL_NAME,fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {

        // SharedPreferences editor is read write or delete SharedPreferences
        editor =sharedPreferences.edit() // this step is like an open my register
        // below step is like you write down in your register
        editor.putBoolean(PrefConstant.IS_LOGGED_IN,true)
        editor.commit() // Apply the all changes in sharedPreference
    }
}