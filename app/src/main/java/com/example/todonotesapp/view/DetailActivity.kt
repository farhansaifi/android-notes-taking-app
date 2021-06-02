package com.example.todonotesapp.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.R

class DetailActivity : AppCompatActivity() {

    val TAG = "DetailActivity"
    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //binding the views function
        bindViews();

        // setup intent function
        setupIntentData();
    }

    private fun bindViews() {

        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription =findViewById(R.id.textViewDescription)
    }

    private fun setupIntentData() {

        val intent = intent // getIntent()
        val title = intent.getStringExtra(AppConstant.NOTE_TITLE)
        val description = intent.getStringExtra(AppConstant.DESCRIPTION)

        // After that we are going to set text on textView
        textViewTitle.text = title
        textViewDescription.text = description

    }
}