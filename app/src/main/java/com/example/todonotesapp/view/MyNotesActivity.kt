package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todonotesapp.NotesApp
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.clicklistener.ItemClickListener
import com.example.todonotesapp.db.Notes
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.workmanager.MyWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

class MyNotesActivity :AppCompatActivity() {

    val tag ="MyNotesActivity"
    var fullName : String? = null
    lateinit var fabAddNotes :FloatingActionButton
    lateinit var sharedPreferences : SharedPreferences
    lateinit var recyclerViewNotes : RecyclerView
    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        // create a function for binding data instead of findViewById(R.id.________)
        bindView()
        // set up sharedPreference function for this activity
        setupSharedPreference()
        // create a function for getting data from Intent
        getIntentData()
        // Create a function for get data from database
        getDataFromDataBase()
        // Function for Get name from previous activity and to set on action bar title
        setUpToolbar()
        // Create a function for clickListener
        clickListeners()
        // create a function for setting up recyclerView
        setupRecyclerView()
        // create a function for work_manager
        setupWorkManager()
    }

    // This function is runs a database delete query in every 15 min
    // and than that note card's check_box is checked means note is completed
    // This function or work_manager delete that note from the database
    private fun setupWorkManager() {

        // constraints is like a condition definer
        // like app runs only charging time, runs only mobile data is connected etc...
        val constraints = Constraints.Builder()
            //.setRequiresCharging(true)--->>(This is an example how to use constraints)
            .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java,
            1,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(request)
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

    private fun getDataFromDataBase() {
        // Get data from the database
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())

        Log.d(tag,notesList.size.toString())
    }

    private fun setUpToolbar() {
        if (supportActionBar != null){
            supportActionBar?.title = fullName
        }
    }

    private fun clickListeners() {
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                // Set up a function for dialog box for adding a new note
                /*setupDialogBox()*/

                // Start a new activity for adding a not and disable the dialog_box
                val intent = Intent(this@MyNotesActivity,AddNotesActivity::class.java)
                startActivityForResult(intent,AppConstant.ADD_NOTES_CODE)
            }

        })
    }

    private fun setupRecyclerView() {
        Log.d(tag,"RecyclerView Works Fine")

        // create interface for ItemClickListener
        val itemClickListener = object  :ItemClickListener{
            override fun onClick(notes: Notes) {

                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.NOTE_TITLE,notes.title)
                intent.putExtra(AppConstant.DESCRIPTION,notes.description)
                startActivity(intent)
            }
            override fun onUpdate(notes: Notes) {

                // Update the value of notes
                Log.d(tag,notes.isTaskCompleted.toString())
                // updating a note in database
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList,itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }

    // This function for second activity to get data for previous activity without startActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppConstant.ADD_NOTES_CODE){

            // Get data from addNotesActivity
            val title : String? = data?.getStringExtra(AppConstant.NOTE_TITLE)
            val description : String? = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath : String? = data?.getStringExtra(AppConstant.IMAGE_PATH)

            Log.d(tag,title.toString())
            Log.d(tag,description.toString())
            Log.d(tag,imagePath.toString())

            // Saving data in local database room_database
            val notesApp = applicationContext as NotesApp
            val notesDao = notesApp.getNotesDb().notesDao()
            val notes = Notes (title = title!!,description = description!!, imagePath = imagePath!!)
            // Data send to recyclerView list
            notesList.add(notes)
            // Data insert to database
            notesDao.insert(notes)
            // After that update a recyclerView adapter
            // list position is last position code
            recyclerViewNotes.adapter?.notifyItemChanged(notesList.size-1)
        }
    }
    // create three dot menu in MyNotesActivity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Adding a resource menu file
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.blog){
            Log.d(tag,"Click success")
            val intent = Intent(this,BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    // We are comment out the function set_up_dialog_box it's not use anymore
    /*private fun setupDialogBox() {

        // we have inflate a layout from the MyNotesActivity screen and than pass inflate
        val view = LayoutInflater.from(this@MyNotesActivity)
            .inflate(R.layout.add_notes_dialog_layout,null)

        // After that we mapping app all widgets form add_notes_dialog_layout using view.findViewById
        // Using view.findViewById because add_notes_dialog_layout it is belong to dialogBox not activity
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
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

                    val notes = Notes(title = title, description = description)
                    //after that add in the array_list these properties (title,description)
                    notesList.add(notes)
                    addNotesToDb(notes)

                } else {

                    Toast.makeText(this@MyNotesActivity,"Title or Description can't be EMPTY"
                        ,Toast.LENGTH_LONG).show()
                }
                // After this submission now we hide the dialog box
                dialog.hide()
            }
        })
        // Show or popup the dialog box after click on fab button
        dialog.show()
    }*/

    private fun addNotesToDb(notes: Notes) {

        // Insert or creating a notes in database
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }
}