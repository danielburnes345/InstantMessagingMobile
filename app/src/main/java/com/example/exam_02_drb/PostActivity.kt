package com.example.exam_02_drb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title = findViewById<TextView>(R.id.postname)

        val id = intent.getIntExtra("id", 0)
        val username = intent.getStringExtra("username").toString()
        title.text  = "Posts by: " + username
        val recyclerView: RecyclerView = findViewById(R.id.users_recycler)
        val rtnbtn : Button = findViewById(R.id.rtn_users)
        recyclerView.layoutManager = LinearLayoutManager(this) // Vertical list
        val postList = mutableListOf<Post>() // Use this as a dynamic list
        recyclerView.adapter = PostAdapter(postList,this,username)
        RetrofitInstance.api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    val filteredPosts = posts?.filter { it.userId == id } ?: emptyList()
                    runOnUiThread {
                        postList.clear()
                        postList.addAll(filteredPosts ?: emptyList()) // Update the list dynamically
                        recyclerView.adapter?.notifyDataSetChanged() // Notify adapter about changes
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Handle failure
                Log.e("RETROFIT_FAILURE", "Error: ${t.message}")
            }
        })




        rtnbtn.setOnClickListener(){

            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)

        }
    }
}