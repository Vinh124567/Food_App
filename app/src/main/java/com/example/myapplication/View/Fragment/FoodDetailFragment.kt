
package com.example.myapplication.View.Fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.databinding.FragmentFoodDetailBinding
import com.example.myapplication.model.Food


class FoodDetailFragment : Fragment(R.layout.fragment_food_detail) {
    private val args: FoodDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentFoodDetailBinding
    private lateinit var foodViewModel: FoodViewModel
    var coutn:Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodViewModel = (activity as MainActivity).foodViewModel
        binding = FragmentFoodDetailBinding.bind(view)
        var food = args.food
        setContent(food)


        binding.imgAdd.setOnClickListener {
            coutn++
            binding.textView11.text = coutn.toString()

        }
        binding.imgReduce.setOnClickListener {
            if (coutn==0){

            }else {
                coutn--
                binding.textView11.text = coutn.toString()
            }

        }

    }

    private fun setContent(food: Food) {
        binding.txtName.text = food.name
        binding.textView10.text=food.description
        Glide.with(this)
            .load(food.image)
            .placeholder(R.drawable.image_newspaper)
            .error(R.drawable.image_newspaper)
            .into(binding.imageView4)
    }



}