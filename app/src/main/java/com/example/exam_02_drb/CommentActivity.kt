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

class CommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_comments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title = findViewById<TextView>(R.id.postname)

        val id = intent.getIntExtra("id", 0)
        val username = intent.getStringExtra("username")
        val userId = intent.getIntExtra("userId",0)
         title.text  = "Comments in post: " + intent.getStringExtra("postname").toString()
        val recyclerView: RecyclerView = findViewById(R.id.users_recycler)
        val rtnbtn : Button = findViewById(R.id.rtn_users)
        recyclerView.layoutManager = LinearLayoutManager(this) // Vertical list
        val commentList = mutableListOf<Comment>() // Use this as a dynamic list
        recyclerView.adapter = CommentAdapter(commentList,this)
        RetrofitInstance.api.getComments().enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {

                    val comments = response.body()
                    Log.d("RETROFIT_SUCCESS", "Number of comments received: ${comments?.size}")
                    runOnUiThread {
                        commentList.clear()
                        commentList.addAll(comments ?: emptyList()) // Update the list dynamically
                        recyclerView.adapter?.notifyDataSetChanged() // Notify adapter about changes
                    }
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                // Handle failure
                Log.e("RETROFIT_FAILURE", "Error: ${t.message}")
            }
        })




        rtnbtn.setOnClickListener(){

            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("id", userId) // Pass data using a key
            intent.putExtra("username", username) // Pass data using a key
            startActivity(intent)

        }
    }
}