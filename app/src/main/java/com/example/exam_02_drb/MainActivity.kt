package com.example.exam_02_drb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val butcreditos = findViewById<Button>(R.id.credits)
        val butexit = findViewById<Button>(R.id.exit)
        val butusers = findViewById<Button>(R.id.users)
        butcreditos.setOnClickListener(){
            val intent = Intent(this, CreditosActivity::class.java)
            startActivity(intent)
        }
        butexit.setOnClickListener(){
            finishAffinity()
        }
        butusers.setOnClickListener(){
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }

    }
}