package com.example.todonotesapp.clicklistener

import com.example.todonotesapp.db.Notes


interface ItemClickListener {

    fun onClick(notes: Notes)

    // This clickListener is for recyclerView checkBox
    fun onUpdate(notes: Notes)
}