package com.example.todonotesapp.data.local.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// This ia a data class
// Will basically defines a structure of table we are creating

// Create this class to data type means DataClass
// Entity takes the table name what we are going to define
@Entity(tableName = "notesData")

data class Notes (

    // Creating some variables for database
    @PrimaryKey(autoGenerate =  true)
    var id: Int? =null,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "imagePath")
    var imagePath: String = "",
    @ColumnInfo(name = "isTaskCompleted")
    var isTaskCompleted: Boolean = false
)