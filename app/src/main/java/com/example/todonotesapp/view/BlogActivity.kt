package com.example.todonotesapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.BlogsAdapter
import com.example.todonotesapp.model.BlogResponse


class BlogActivity : AppCompatActivity() {

    lateinit var recyclerViewBlogs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("https://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(BlogResponse::class.java, object : ParsedRequestListener<BlogResponse> {
                override fun onResponse(response: BlogResponse?) {

                    setupRecyclerView(response)
                }

                override fun onError(anError: ANError?) {

                    Log.d("BlogActivity",anError?.localizedMessage.toString())
                }
            })
    }

    private fun bindViews() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }

    // create a function for setup recyclerView on activity
    private fun setupRecyclerView(response: BlogResponse?) {
        val blogAdapter = BlogsAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }
}