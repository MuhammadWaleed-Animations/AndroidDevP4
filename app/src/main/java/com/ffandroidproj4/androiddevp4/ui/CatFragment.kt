package com.ffandroidproj4.androiddevp4.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ffandroidproj4.androiddevp4.R
import com.ffandroidproj4.androiddevp4.adapter.CatRecyclerViewAdapter
import com.ffandroidproj4.androiddevp4.model.CatModel
import com.ffandroidproj4.androiddevp4.preferences.PreferenceManager
import com.ffandroidproj4.androiddevp4.repository.CatRepository
import com.google.gson.reflect.TypeToken

class CatFragment : Fragment() {

    private var catList = ArrayList<CatModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getView()?.findViewById<TextView>(R.id.tvCat)?.setText("meow")

        fetchCats()

    }
////old without preference(Data saving)
//    private fun fetchCats() {
//
//
//
//        val itemRecyclerView = getView()?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.cfRecyclerView)
//        itemRecyclerView?.layoutManager = LinearLayoutManager(context)
//
//        CatRepository.getCats(
//            onResult = { students ->
//                students?.forEach { student ->
//                    Log.d("MainActivity", "Name: ${student.name}, Origin: ${student.origin}, Description: ${student.description}")
//                    catList.add(CatModel(name = student.name, origin = student.origin, description = student.description))
//                }
//                val catRecyclerViewAdapter = CatRecyclerViewAdapter(catList)
//                itemRecyclerView?.adapter = catRecyclerViewAdapter
//            },
//            onError = { error ->
//                Log.e("MainActivity", "Error: ${error.message}")
//            }
//        )
//    }
    private fun fetchCats() {
        val itemRecyclerView = getView()?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.cfRecyclerView)
        itemRecyclerView?.layoutManager = LinearLayoutManager(context)



        // Check if student data is already saved in SharedPreferences
        val savedCatsJson = PreferenceManager.get("cats", "")
        if (!savedCatsJson.isNullOrEmpty()) {
            // Parse saved student data and log it
            val cats: List<CatModel> = deserializeStudents(savedCatsJson)
            cats.forEach { student ->
                Log.d("MainActivity Cats Preference", "Namee: ${student.name}, Originn: ${student.origin}, Descriptionn: ${student.description}")
                catList.add(CatModel(name = student.name, origin = student.origin, description = student.description))
            }
            val catRecyclerViewAdapter = CatRecyclerViewAdapter(catList)
            itemRecyclerView?.adapter = catRecyclerViewAdapter

        } else {
            // Fetch student data from repository (API call)
            CatRepository.getCats(
                onResult = { students ->
                    students?.forEach { student ->
                        Log.d("MainActivity Cats Api", "Name: ${student.name}, Origin: ${student.origin}, Description: ${student.description}")
                        catList.add(CatModel(name = student.name, origin = student.origin, description = student.description))
                    }
                    val catRecyclerViewAdapter = CatRecyclerViewAdapter(catList)
                    itemRecyclerView?.adapter = catRecyclerViewAdapter

                    val catsJson = serializeStudents(students)
                    PreferenceManager.save("cats", catsJson)
                },
                onError = { error ->
                    Log.e("MainActivity", "Error: ${error.message}")
                }
            )
        }
    }

    private fun serializeStudents(students: List<CatModel>?): String {
        // Serialize the list of students to JSON
        return PreferenceManager.gson.toJson(students)
    }

    private fun deserializeStudents(data: String): List<CatModel> {
        // Deserialize the JSON string to a list of students
        return PreferenceManager.gson.fromJson(data, object : TypeToken<List<CatModel>>() {}.type)
    }
}