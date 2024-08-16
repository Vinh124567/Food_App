
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
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var foodViewModel: FoodViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodViewModel = (activity as MainActivity).foodViewModel
        _binding = FragmentFoodDetailBinding.bind(view)
        val food =args.food

        setContent(food)

        foodViewModel.count.observe(viewLifecycleOwner) { count ->
            binding.textView11.text = count.toString()
            binding.txtPrice.text = (count * food.price).toString()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgAdd.setOnClickListener {
            adjustCount(true)
        }

        binding.imgReduce.setOnClickListener {
            adjustCount(false)
        }

        binding.btnOrder.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("food",food)
                putInt("count", foodViewModel.count.value ?: 0)
            }
            findNavController().navigate(R.id.action_foodDetailFragment2_to_paymentFragment, bundle)
        }
    }

    private fun adjustCount(increment: Boolean) {
        val currentCount = foodViewModel.count.value ?: 1
        val newCount = if (increment) currentCount + 1 else currentCount - 1
        if (newCount > 0) foodViewModel.setCount(newCount)
    }

    private fun setContent(food: Food) {
        binding.apply {
            txtName.text = food.name
            txtPrice.text = food.price.toString()
            textView10.text = food.description
            Glide.with(this@FoodDetailFragment)
                .load(food.image)
                .placeholder(R.drawable.image_newspaper)
                .error(R.drawable.image_newspaper)
                .into(imageView4)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}