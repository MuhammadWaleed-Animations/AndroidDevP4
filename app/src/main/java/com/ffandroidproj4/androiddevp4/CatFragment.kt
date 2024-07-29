package com.ffandroidproj4.androiddevp4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ffandroidproj4.androiddevp4.adapter.CatRecyclerViewAdapter
import com.ffandroidproj4.androiddevp4.model.CatModel
import com.ffandroidproj4.androiddevp4.repository.CatRepository

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

    private fun fetchCats() {
        val itemRecyclerView = getView()?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.cfRecyclerView)
        itemRecyclerView?.layoutManager = LinearLayoutManager(context)

        CatRepository.getCats(
            onResult = { students ->
                students?.forEach { student ->
                    Log.d("MainActivity", "Name: ${student.name}, Origin: ${student.origin}, Description: ${student.description}")
                    catList.add(CatModel(name = student.name, origin = student.origin, description = student.description))
                }
                val catRecyclerViewAdapter = CatRecyclerViewAdapter(catList)
                itemRecyclerView?.adapter = catRecyclerViewAdapter
            },
            onError = { error ->
                Log.e("MainActivity", "Error: ${error.message}")
            }
        )
    }
}