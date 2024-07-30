package com.ffandroidproj4.androiddevp4.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ffandroidproj4.androiddevp4.R
import com.ffandroidproj4.androiddevp4.adapter.DogAdapter
import com.ffandroidproj4.androiddevp4.model.Dog
import com.ffandroidproj4.androiddevp4.preferences.PreferenceManager
import com.ffandroidproj4.androiddevp4.repository.DogRepository
import com.google.gson.reflect.TypeToken

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

//    private fun fetchDogs() {
//        val itemRecyclerView = getView()?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.dfRecyclerView)
//        itemRecyclerView?.layoutManager = LinearLayoutManager(context)
//
//        DogRepository.getDogs(
//            onResult = { students ->
//                students?.forEach { student ->
//                    Log.d("MainActivity", "Name: ${student.name}, Origin: ${student.origin}, Description: ${student.description}")
//                    dogList.add(Dog(name = student.name, origin = student.origin, description = student.description,lifespan = student.lifespan))
//                }
//                val catRecyclerViewAdapter = DogAdapter(dogList)
//                itemRecyclerView?.adapter = catRecyclerViewAdapter
//            },
//            onError = { error ->
//                Log.e("MainActivity", "Error: ${error.message}")
//            }
//        )
//    }





    private fun fetchDogs()
    {
        val itemRecyclerView = getView()?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.dfRecyclerView)
        itemRecyclerView?.layoutManager = LinearLayoutManager(context)

        //applying check to see if dogs data is already present in Shared.pf

        val savedDogsJson=PreferenceManager.get("dogs","")


        fun serializeDogs(students: List<Dog>?): String
        {
            // Serialize the list of students to JSON
            return PreferenceManager.gson.toJson(students)
        }

        fun deserializeDogs(data: String): List<Dog>
        {
            // Deserialize the JSON string to a list of students
            return PreferenceManager.gson.fromJson(data, object : TypeToken<List<Dog>>() {}.type)
        }

        if(!savedDogsJson.isNullOrEmpty())
        {
            Log.d("MainActivity Dogs Preference", "Utha rha ha data")
            //parse saved dog data and display in recycler view
            val dogs: List<Dog> = deserializeDogs(savedDogsJson)

            val dogAdapter=DogAdapter(dogs)
            itemRecyclerView?.adapter=dogAdapter
        }
        else
        {
            Log.d("MainActivity Dogs Api", "la rha ha data")

            //if no data found , fetch from repository
            DogRepository.getDogs(

                onResult = {dogs->
                    if(dogs!=null)
                    {
                        val dogsJsonvar=serializeDogs(dogs)
                        PreferenceManager.save("dogs",dogsJsonvar)

                        //recycler view
                        val dogAdapter=DogAdapter(dogs)
                        itemRecyclerView?.adapter=dogAdapter

                    }else
                    {
                        Log.e("Main Activity","No dogs found ")
                    }

                },
                onError = { error ->
                    Log.e("MainActivity", "Error: ${error.message}")
                }
            )
        }



    }

}