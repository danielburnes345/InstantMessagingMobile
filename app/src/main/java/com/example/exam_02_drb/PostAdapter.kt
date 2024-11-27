package com.example.exam_02_drb


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val postList: List<Post>,private val context: Context,private val username : String) : RecyclerView.Adapter<PostAdapter.ItemViewHolder>() {

    // Define the ViewHolder
    class ItemViewHolder(item_layout: View) : RecyclerView.ViewHolder(item_layout) {
        val title: Button = item_layout.findViewById(R.id.item_button)
    }

    // Inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    // Bind data to the views
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = postList[position]
        holder.title.text = "id: "+ item.id + " title: " + item.title
        holder.title.setOnClickListener {
            val intent = Intent(context,  CommentActivity :: class.java)
            intent.putExtra("userId",item.userId)
            intent.putExtra("username",username)
            intent.putExtra("id", item.id) // Pass data using a key
            intent.putExtra("postname", item.title) // Pass data using a key
            context.startActivity(intent)
        }
    }

    // Return the size of the data list
    override fun getItemCount() = postList.size
}