package com.example.exam_02_drb

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View


import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(
    private val userList: List<User>,
    private val context: Context,
    private val onLongPress: (User) -> Unit // Callback for long press
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(item_layout: View) : RecyclerView.ViewHolder(item_layout) {
        val username: Button = item_layout.findViewById(R.id.item_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = userList[position]
        holder.username.text = user.username

        // Handle regular click
        holder.username.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra("id", user.id)
            intent.putExtra("username", user.username)
            context.startActivity(intent)
        }

        // Handle long press
        holder.username.setOnLongClickListener {
            onLongPress(user) // Pass the long-pressed user to the callback
            true // Return true to indicate the event is consumed
        }
    }

    override fun getItemCount() = userList.size
}

