package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.clicklistener.ItemClickListener
import com.example.todonotesapp.model.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton

public class MyNotesActivity :AppCompatActivity() {

    val TAG ="MyNotesActivity"
    var fullName : String? = null
    lateinit var fabAddNotes :FloatingActionButton
    lateinit var sharedPreferences : SharedPreferences
    lateinit var recyclerViewNotes : RecyclerView
    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        // create a function for binding data instead of findViewById(R.id.________)
        bindView();

        // set up sharedPreference for this activity
        setupSharedPreference();

        // create a function for getting data from Intent
        getIntentData();

        // Get name from previous activity and to set on action bar title
        supportActionBar?.title = fullName

        //Add OnclickListener on fab button
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                // Set up a function for dialog box for adding a new note
                setupDialogBox()
            }

        })

    }

    private fun setupDialogBox() {

        // we have inflate a layout from the MyNotesActivity screen and than pass inflate
        val view = LayoutInflater.from(this@MyNotesActivity)
            .inflate(R.layout.add_notes_dialog_layout,null)

        // After that we mapping app all widgets form add_notes_dialog_layout using view.findViewById
        // Using view.findViewById because add_notes_dialog_layout it is belong to dialogBox not activity
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescriptioin)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)

        // Below we create a dialog box
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()

        // Implement a ClickListener for button submit
        buttonSubmit.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)){

                    // Get Notes data class constructors
                    val notes = Notes (title, description)

                    //after that add in the array_list these properties (title,description)
                    notesList.add(notes)
                } else {

                    Toast.makeText(this@MyNotesActivity,"Title or Description can't be EMPTY"
                    ,Toast.LENGTH_LONG).show()
                }

                // create a function for setting up recyclerView
                setupRecyclerView()

                // After this submission now we hide the dialog box
                dialog.hide()
            }

        })

        // Show or popup the dialog box after click on fab button
        dialog.show()
    }

    private fun setupRecyclerView() {
        Log.d(TAG,"RecyclerView Works Fine")

        // create interface for ItemClickListener
        val itemClickListener = object  :ItemClickListener{
            override fun onClick(notes: Notes) {

                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.NOTE_TITLE,notes.title)
                intent.putExtra(AppConstant.DESCRIPTION,notes.description)
                startActivity(intent)
            }
        }
        val notesAdapter = NotesAdapter(notesList,itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }

    private fun bindView() {
       fabAddNotes = findViewById(R.id.fabAddNotes)
       recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setupSharedPreference() {

        // Set up sharedPreference
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun getIntentData() {

        // Create intent for get data from previous activity
        val intent = intent
        fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        if (intent.hasExtra(AppConstant.FULL_NAME)) {

            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "")!!
        }
    }
}