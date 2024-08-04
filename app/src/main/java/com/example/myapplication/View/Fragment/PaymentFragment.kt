package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.databinding.FragmentPaymentBinding
import com.example.myapplication.model.Food
import com.example.myapplication.model.User


class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private val args: PaymentFragmentArgs by navArgs()
    private lateinit var binding: FragmentPaymentBinding
    private var count:Int = 0
    private lateinit var foodViewModel: FoodViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodViewModel = (activity as MainActivity).foodViewModel
        binding = FragmentPaymentBinding.bind(view)
        val food = args.food
         count = args.count
        foodViewModel._loginResult.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                setContent(food,it,count)
            }
        })

    }
    private fun setContent(food: Food,user: User,count:Int) {
        binding.txtQuantity.text=count.toString()
        binding.txtCustomerName.text=user.name.toString()
        binding.txtPhone.text=user.phoneNumber.toString()
        binding.txtAddress.text=user.address.toString()
        binding.txtHouseNo.text=user.houseNumber.toString()
        binding.txtCity.text=user.City.toString()
        binding.txtName.text = food.name
        binding.txtPrice.text= food.price.toString()
        Glide.with(this)
            .load(food.image)
            .placeholder(R.drawable.image_newspaper)
            .error(R.drawable.image_newspaper)
            .into(binding.imageViewUnder)
    }
}