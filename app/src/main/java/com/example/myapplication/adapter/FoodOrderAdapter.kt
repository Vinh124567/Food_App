package com.example.myapplication.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.OrderDTO

class FoodOrderAdapter: RecyclerView.Adapter<FoodOrderAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageViewUnder)
        val name: TextView = itemView.findViewById(R.id.foodName)
        val orderAt:TextView=itemView.findViewById(R.id.foodId)
    }

    private val differCallback = object : DiffUtil.ItemCallback<OrderDTO>() {
        override fun areItemsTheSame(oldItem: OrderDTO, newItem: OrderDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderDTO, newItem: OrderDTO): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_under, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val orderDTO = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(orderDTO.image)
            .placeholder(R.drawable.image_newspaper)
            .error(R.drawable.image_newspaper)
            .into(holder.image)

        holder.name.text = orderDTO.foodName
        holder.orderAt.text = orderDTO.localDateTime.toString()

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(orderDTO) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((OrderDTO) -> Unit)? = null

    fun setOnItemClickListener(listener: (OrderDTO) -> Unit) {
        onItemClickListener = listener
    }
}

