package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.View.viewmodel.OrderViewModel
import com.example.myapplication.databinding.FragmentPaymentBinding
import com.example.myapplication.model.Food
import com.example.myapplication.model.Order
import com.example.myapplication.model.User


class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private val args: PaymentFragmentArgs by navArgs()
    private lateinit var binding: FragmentPaymentBinding
    private var count:Int = 0
    lateinit var food: Food
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)
        orderViewModel= (activity as MainActivity).orderViewModel
        authViewModel = (activity as MainActivity).authViewModel


        food = args.food
        count = args.count

        authViewModel.authResult.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                user.first?.uid?.let { authViewModel.getUserDetail(it) }
            } else {
            }
        })

        authViewModel.userDetail.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                setContent(food, it, count)
                val order = order(it, food)
                if (order != null) {
                    binding.btnPayment.setOnClickListener {
                        orderViewModel.addNewOrder(order)
                        findNavController().navigate(R.id.action_paymentFragment_to_yourOrdersFragment)
                    }
                } else {
                    Toast.makeText(context, "Cannot create order due to invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setContent(food: Food,user:User,count:Int) {
        val total=count*food.price
        binding.txtQuantity.text=count.toString()
        binding.txtCustomerName.text=user.name.toString()
        binding.txtPhone.text=user.phoneNumber.toString()
        binding.txtTotal.text=total.toString()
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

    fun order(user: User,food: Food):Order{
        var userId=user.id
        var foodId=food.id
        var total=binding.txtTotal.text.toString().toInt()
        val quantity: Int = binding.txtQuantity.text.toString().toInt()
        return Order(0,userId,foodId,quantity,"Processing",total)
    }
}