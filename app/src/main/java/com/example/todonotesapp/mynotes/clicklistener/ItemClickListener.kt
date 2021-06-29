package com.example.todonotesapp.mynotes.clicklistener

import com.example.todonotesapp.data.local.db.Notes


interface ItemClickListener {

    fun onClick(notes: Notes)

    // This clickListener is for recyclerView checkBox
    fun onUpdate(notes: Notes)
}