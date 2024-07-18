package com.example.myapplication.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(){
    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private lateinit var image: ImageView
    private lateinit var name: TextView
    private lateinit var price: TextView
    private lateinit var description: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}