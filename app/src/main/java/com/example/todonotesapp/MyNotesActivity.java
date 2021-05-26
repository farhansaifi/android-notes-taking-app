package com.example.todonotesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todonotesapp.adapter.NotesAdapter;
import com.example.todonotesapp.clicklistener.ItemClickListener;
import com.example.todonotesapp.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;
    SharedPreferences sharedPreferences;

    // create a variable for recyclerView type for recyclerView
    RecyclerView recyclerViewNotes;

    // Create a arrayList for recyclerView
    // In array list type is not (string,int,float, etc) it's our custom type Notes
    // Notes == We make a package and create a Notes file in the package
    // this is our custom type
    ArrayList<Notes> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        // create a function for binding data instead of findViewById(R.id.________)
        bindView();
        // set up sharedPreference for this activity
        setupSharedPreference();
        // create a function for getting data from Intent
        getIntentData();


        //Add OnclickListener on fab button
        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set up a dialog box for adding a new note
                setupDialogBox();
            }
        });

        // Get name from previous activity and to action bar title
        getSupportActionBar().setTitle(fullName);
    }

    private void setupSharedPreference() {

        // Set uo sharedPreference
        sharedPreferences = getSharedPreferences(PrefConstant.INSTANCE.getSHARED_PREFERENCE_NAME(),MODE_PRIVATE);

    }

    private void getIntentData() {

        // Create intent for get data from previous activity
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstant.INSTANCE.getFULL_NAME());

        // if intent Full Name data is empty so we get FUll Name data from sharedPreference
        if (TextUtils.isEmpty(fullName)){
            fullName =sharedPreferences.getString(PrefConstant.INSTANCE.getFULL_NAME(),"");

            // Assigning a log for checking purpose
            Log.d("saved_data" , fullName);
        }
    }

    // Binding the views here
    private void bindView() {

        fabAddNotes = findViewById(R.id.fabAddNotes);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

    }

    private void setupDialogBox() {

        // we have inflate a layout from the MyNotesActivity screen and than pass inflate
        View view = LayoutInflater.from(MyNotesActivity.this)
                .inflate(R.layout.add_notes_dialog_layout,null);

        // After that we mapping app all widgets form add_notes_dialog_layout using view.findViewById
        // Using view.findViewById because add_notes_dialog_layout it is belong to dialogBox not activity
        EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        EditText editTextDescription = view.findViewById(R.id.editTextDescriptioin);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);

        // Android does not have a popup Android have a dialog box
        // Show Dialog Box
        final AlertDialog dialog = new AlertDialog.Builder(MyNotesActivity.this)
                .setView(view)
                .setCancelable(false)
                .create();

        // Snippet of code of submit button for getText and setText
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {

                    // create a variable for Notes
                    Notes notes = new Notes();

                    // set title and description in Notes set method
                    notes.setTitle(title);
                    notes.setDescription(description);

                    //after that add in the array_list these properties (title,description)
                    notesList.add(notes);

                    Log.d("MyNotesActivity", String.valueOf(notesList.size()));

                } else {

                    Toast.makeText(MyNotesActivity.this,
                            "Title or Description can't be EMPTY",Toast.LENGTH_SHORT).show();
                }

                // create a method for setting up recyclerView
                setupRecyclerView();

                // After this submission now we hide the dialog box
                dialog.hide();
            }
        });
        dialog.show();
    }

    private void setupRecyclerView() {
        // create interface for ItemClickListener
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Notes notes) {

                Intent intent = new Intent(MyNotesActivity.this,DetailActivity.class);
                intent.putExtra(AppConstant.INSTANCE.getNOTE_TITLE(),notes.getTitle());
                intent.putExtra(AppConstant.INSTANCE.getDESCRIPTION(),notes.getDescription());
                startActivity(intent);
            }
        };

        // first of all we declare variable type of NotesAdapter
        NotesAdapter notesAdapter = new NotesAdapter(notesList,itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);

    }
}