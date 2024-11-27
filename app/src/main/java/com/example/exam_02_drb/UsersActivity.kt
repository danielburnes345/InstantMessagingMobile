package com.example.exam_02_drb

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsersActivity : AppCompatActivity() {
    private lateinit var userDatabase: UserDatabase
    private lateinit var userDao: UserDao


    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected == true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        userDatabase = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user_database"
        ).build()
        userDao = userDatabase.userDao()
        setContentView(R.layout.layout_users)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.users_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val userList = mutableListOf<User>()
        val rtn_button = findViewById<Button>(R.id.rtn_users)
        recyclerView.adapter = ItemAdapter(userList, this) { user ->
            CoroutineScope(Dispatchers.IO).launch {
                userDao.insertUser(user) // Save the long-pressed user to the database
            }
            runOnUiThread {
                Toast.makeText(this@UsersActivity, "${user.username} saved locally", Toast.LENGTH_SHORT).show()
            }
        }


        Log.d("hey",isConnectedToInternet().toString())
        if (isConnectedToInternet()) {
            // Cargar usuarios desde la API
            RetrofitInstance.api.getUsers().enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val users = response.body()
                        Log.d("dhhd",users.toString())
                        runOnUiThread {
                            userList.clear()
                            userList.addAll(users ?: emptyList())
                            recyclerView.adapter?.notifyDataSetChanged()
                        }
                        // Guardar los usuarios en la base de datos
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.e("RETROFIT_FAILURE", "Error: ${t.message}")
                }
            })
        } else {
            // Cargar usuarios desde Room
            CoroutineScope(Dispatchers.IO).launch {
                val usersFromDb = userDao.getAllUsers()
                runOnUiThread {
                    userList.clear()
                    userList.addAll(usersFromDb)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }

        rtn_button.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
