package com.example.todonotesapp.mynotes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todonotesapp.R
import com.example.todonotesapp.mynotes.clicklistener.ItemClickListener
import com.example.todonotesapp.data.local.db.Notes

class NotesAdapter (val list: List<Notes>, val itemClickListener:ItemClickListener)
    :RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // get list and its variable's position
        val notes = list[position]

        //variables get data from list using position
        val title = notes.title
        val description = notes.description

        // setting up data to recyclerView card using upper variables
        holder.textViewTitle.text = title
        holder.textViewDescription.text = description
        holder.checkBoxMarkStatus.isChecked = notes.isTaskCompleted
        // set image on imageView using glide
        Glide.with(holder.imageView).load(notes.imagePath).into(holder.imageView)

        Log.d("jkml",notes.imagePath)
        // assign setOnClickListener on card;s item
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                itemClickListener.onClick(notes)
            }
        })
        // Add check change listener
        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notes.isTaskCompleted = isChecked
                itemClickListener.onUpdate(notes)
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
        val checkBoxMarkStatus :CheckBox = itemView.findViewById(R.id.checkBoxMarkStatus)
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
    }
}