package com.example.todonotesapp

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.example.todonotesapp.data.local.db.NotesDatabase

// This class is making for access the database

class NotesApp: Application() {
    override fun onCreate() {
        super.onCreate()

        // this is require for amit-shekhar fast networking library
        AndroidNetworking.initialize(applicationContext);

    }

    fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }
}