
package com.example.myapplication.View.Fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    var count: Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodViewModel = (activity as MainActivity).foodViewModel
        binding = FragmentFoodDetailBinding.bind(view)

        foodViewModel.food.observe(viewLifecycleOwner) { food ->
            if (food != null) {
                setContent(food)
            }
        }

        foodViewModel.count.observe(viewLifecycleOwner) { count ->
            binding.textView11.text = count.toString()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgAdd.setOnClickListener {
            val newCount = (foodViewModel.count.value ?: 0) + 1
            foodViewModel.setCount(newCount)
        }

        binding.imgReduce.setOnClickListener {
            val currentCount = foodViewModel.count.value ?: 0
            if (currentCount > 0) {
                val newCount = currentCount - 1
                foodViewModel.setCount(newCount)
            }
        }

        binding.btnOrder.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("food", foodViewModel.food.value)
                putInt("count", foodViewModel.count.value ?: 0)
            }
            findNavController().navigate(R.id.action_foodDetailFragment2_to_paymentFragment, bundle)
        }

        if (foodViewModel.food.value == null) {
            val food = args.food
            foodViewModel.setFood(food)
        }
    }

    private fun setContent(food: Food) {
        binding.txtName.text = food.name
        binding.txtPrice.text = food.price.toString()
        binding.textView10.text = food.description
        Glide.with(this)
            .load(food.image)
            .placeholder(R.drawable.image_newspaper)
            .error(R.drawable.image_newspaper)
            .into(binding.imageView4)
    }

    private fun updatePrice(pricePerItem: Int) {
        val totalPrice = if (count == 0) 0 else count * pricePerItem
        binding.txtPrice.text = totalPrice.toString()
    }

}