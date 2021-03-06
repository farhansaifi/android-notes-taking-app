package com.example.todonotesapp.data.local.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


// we created some functions for query update delete allData

// data access objects = DAO
@Dao
interface NotesDao{

    @Query("SELECT * from notesData")
    fun getAll() : List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    // Create a function for work_manager
    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status:Boolean)
}