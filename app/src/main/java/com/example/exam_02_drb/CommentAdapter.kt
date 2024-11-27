package com.example.exam_02_drb


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter(private val commentList: List<Comment>,private val context: Context) : RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {

    // Define the ViewHolder
    class ItemViewHolder(comment_layout: View) : RecyclerView.ViewHolder(comment_layout) {
        val content: TextView = comment_layout.findViewById(R.id.content)
    }

    // Inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_layout, parent, false)
        return ItemViewHolder(view)
    }

    // Bind data to the views
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = commentList[position]
        holder.content.text = "id: "+ item.id + " title: " + item.name + "body: " + item.body
    }

    // Return the size of the data list
    override fun getItemCount() = commentList.size
}