package com.example.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.R
import com.example.todonotesapp.clicklistener.ItemClickListener
import com.example.todonotesapp.model.Notes

class NotesAdapter (val list: List<Notes>,val itemClickListener:ItemClickListener)
    :RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {

        // get list and its variable's position
        val notes = list[position]

        //variables get data from list using position
        val title = notes.title
        val description = notes.description

        // setting up data to recyclerView card using upper variables
        holder.textViewTitle.text = title
        holder.textViewDescription.text = description

        // assign setOnClickListener on card;s item
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                itemClickListener.onClick(notes)
            }

        })
    }

    override fun getItemCount(): Int {

        return list.size
    }

    class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView) {

        // this is a constructor of viewHolder its work on
        // findViewById on(create in card layout fields ) variables
        val textViewTitle :TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription :TextView = itemView.findViewById(R.id.textViewDescription)
    }
}