package com.ffandroidproj4.androiddevp4.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ffandroidproj4.androiddevp4.MyApplication
import com.ffandroidproj4.androiddevp4.R
import com.ffandroidproj4.androiddevp4.model.CatModel
import com.ffandroidproj4.androiddevp4.preferences.PreferenceManager
import com.ffandroidproj4.androiddevp4.repository.DogRepository

class MainActivity : AppCompatActivity() {

    private lateinit var btn: androidx.appcompat.widget.AppCompatButton
    private var catList = ArrayList<CatModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //var noIdea = MyApplication()
        PreferenceManager.init(this)
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnNewActivity).setOnClickListener {
            Intent(this, SecondActivity::class.java).also{
                startActivity(it)
            }
        }
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnNewActivity).text = "Click me"

        //fetchCats()
        //fetchDogs()
    }




    private fun fetchDogs() {
//        val itemRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
//        itemRecyclerView.layoutManager = LinearLayoutManager(this)

        DogRepository.getDogs(
            onResult = { students ->
                students?.forEach { student ->
                    Log.d("MainActivity", "Name: ${student.name}, Origin: ${student.origin}, Description: ${student.description}")
                    //catList.add(CatModel(name = student.name, origin = student.origin, description = student.description))
                }
//                val catRecyclerViewAdapter = CatRecyclerViewAdapter(catList)
//                itemRecyclerView.adapter = catRecyclerViewAdapter
            },
            onError = { error ->
                Log.e("MainActivity", "Error: ${error.message}")
            }
        )
    }
}



//    private fun fetchCats() {
//        val itemRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
//        itemRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        CatRepository.getCats(
//            onResult = { students ->
//                students?.forEach { student ->
//                    Log.d("MainActivity", "Name: ${student.name}, Origin: ${student.origin}, Description: ${student.description}")
//                    catList.add(CatModel(name = student.name, origin = student.origin, description = student.description))
//                }
//                val catRecyclerViewAdapter = CatRecyclerViewAdapter(catList)
//                itemRecyclerView.adapter = catRecyclerViewAdapter
//            },
//            onError = { error ->
//                Log.e("MainActivity", "Error: ${error.message}")
//            }
//        )
//    }

