package com.example.todonotesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Create a abstract class this class is create the database

@Database(entities = [Notes::class], version = 1)

abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao():NotesDao

    // This is equivalent to public static means we can access this variable outside of this class
    companion object{
        lateinit var INSTANCE:NotesDatabase
        fun getInstance(context: Context) : NotesDatabase{
            synchronized(NotesDatabase::class){
                INSTANCE = Room.databaseBuilder(context.applicationContext
                    ,NotesDatabase::class.java,"my-notes.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}