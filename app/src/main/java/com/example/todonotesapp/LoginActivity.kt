package com.example.todonotesapp

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
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
        buttonLogin = findViewById(R.id.buttonLogin)
    }

    private fun setUpSharedPreferences() {

        // SharedPreferences editor is read write or delete SharedPreferences
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)

    }
}