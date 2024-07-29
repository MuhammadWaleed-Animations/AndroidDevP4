package com.ffandroidproj4.androiddevp4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ffandroidproj4.androiddevp4.R
import com.ffandroidproj4.androiddevp4.model.Dog

class DogAdapter(private val dogs: List<Dog>) :
    RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogs[position])
    }

    override fun getItemCount() = dogs.size

    class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.text_name)
        private val textOrigin: TextView = itemView.findViewById(R.id.text_origin)
        private val textlifesp: TextView = itemView.findViewById(R.id.text_lifesp)
        private val textdesc: TextView = itemView.findViewById(R.id.text_desc)


        fun bind(dog: Dog)
        {
            textName.text = dog.name
            textOrigin.text = "origin: ${dog.origin}"
            textlifesp.text = "Lifespan: ${dog.lifespan}"
            textdesc.text="Description: ${dog.description}"
        }
    }
}
