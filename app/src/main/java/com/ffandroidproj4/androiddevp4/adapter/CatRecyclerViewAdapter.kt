package com.ffandroidproj4.androiddevp4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ffandroidproj4.androiddevp4.R
import com.ffandroidproj4.androiddevp4.model.CatModel

class CatRecyclerViewAdapter(private val mlist: List<CatModel>):RecyclerView.Adapter<CatRecyclerViewAdapter.ViewHolder>(){


    class ViewHolder(catView: View): RecyclerView.ViewHolder(catView){

        val nameView: TextView = this.itemView.findViewById<TextView>(R.id.catName)
        val originView: TextView = this.itemView.findViewById(R.id.catOrigin)
        val descView: TextView = this.itemView.findViewById(R.id.catDesc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_cat,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val catModel = mlist[position]
        holder.nameView.text = "${catModel.name}"
        holder.originView.text = " ${ catModel.origin }"
        holder.descView.text = "Description:\n ${ catModel.description }"
    }
}

//class CatRecyclerViewAdapter(private val mlist: List<CatModel>):
//    RecyclerView.Adapter<CatRecyclerViewAdapter.ViewHolder>(){
//
//
//    class ViewHolder(catView: View): RecyclerView.ViewHolder(catView){
//
//        val nameView: TextView = this.itemView.findViewById<TextView>(R.id.catName)
//        val originView: TextView = this.itemView.findViewById(R.id.catOrigin)
//        val descView: TextView = this.itemView.findViewById(R.id.catDesc)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.list_cat,parent,false)
//
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return mlist.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val CatModel = mlist[position]
//        holder.nameView.text = CatModel.name
//        holder.originView.text = CatModel.origin
//        holder.descView.text = CatModel.description
//    }
//}