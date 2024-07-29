package com.ffandroidproj4.androiddevp4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, CatFragment() )
            commit()
        }
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton1).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, CatFragment() )
                addToBackStack(null)
                commit()
            }
        }
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton2).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, OtherFragment() )
                addToBackStack(null)
                commit()
            }
        }

    }
}