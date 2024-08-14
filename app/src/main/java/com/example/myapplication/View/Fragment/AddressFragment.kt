package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.databinding.FragmentAddressBinding
import com.example.myapplication.model.User


class AddressFragment : Fragment(R.layout.fragment_address) {
    private lateinit var userId: String
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var address: String
    private lateinit var house: String
    private lateinit var city: String
    private lateinit var binding: FragmentAddressBinding
    private lateinit var foodViewModel: FoodViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddressBinding.bind(view)
        foodViewModel = (activity as MainActivity).foodViewModel
        val firebaseUser=foodViewModel.emailResult.value
        if (firebaseUser != null) {
            email=firebaseUser.email.toString()
            userId=firebaseUser.uid
        }

        foodViewModel = (activity as MainActivity).foodViewModel
        foodViewModel.emailResult.observe(viewLifecycleOwner) { result ->
            result?.let { user ->
                userId = user.uid
            }
        }


            val items = listOf("Item 1", "Item 2", "Item 3")
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, // Layout cho các mục Spinner
                items
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Layout cho dropdown
            }
            binding.spinnerAddress.adapter = adapter

            binding.btnSignUpNow.setOnClickListener {
                var user=createUser()
                foodViewModel.createUser(user);
            }

        }
    fun createUser(): User{
        house = binding.edtHouse.text.toString()
        phone = binding.edtPhone.text.toString()
        address = binding.edtAddress.text.toString()
        city = binding.spinnerAddress.selectedItem.toString()
        return  User (userId,"vinh", email, address, phone, house, city)
    }

    }