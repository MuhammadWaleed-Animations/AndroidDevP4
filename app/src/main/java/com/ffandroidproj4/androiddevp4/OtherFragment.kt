package com.ffandroidproj4.androiddevp4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ffandroidproj4.androiddevp4.adapter.CatRecyclerViewAdapter
import com.ffandroidproj4.androiddevp4.adapter.DogAdapter
import com.ffandroidproj4.androiddevp4.model.CatModel
import com.ffandroidproj4.androiddevp4.model.Dog
import com.ffandroidproj4.androiddevp4.repository.CatRepository
import com.ffandroidproj4.androiddevp4.repository.DogRepository

class OtherFragment : Fragment() {
    private var dogList = ArrayList<Dog>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getView()?.findViewById<TextView>(R.id.tvCat)?.setText("meow")

        fetchDogs()

    }

    private fun fetchDogs() {
        val itemRecyclerView = getView()?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.dfRecyclerView)
        itemRecyclerView?.layoutManager = LinearLayoutManager(context)

        DogRepository.getDogs(
            onResult = { students ->
                students?.forEach { student ->
                    Log.d("MainActivity", "Name: ${student.name}, Origin: ${student.origin}, Description: ${student.description}")
                    dogList.add(Dog(name = student.name, origin = student.origin, description = student.description,lifespan = student.lifespan))
                }
                val catRecyclerViewAdapter = DogAdapter(dogList)
                itemRecyclerView?.adapter = catRecyclerViewAdapter
            },
            onError = { error ->
                Log.e("MainActivity", "Error: ${error.message}")
            }
        )
    }

}