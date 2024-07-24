package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddressBinding


class AddressFragment : Fragment(R.layout.fragment_address) {
    private lateinit var binding: FragmentAddressBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddressBinding.bind(view)
//        val spinner: Spinner = findViewById(R.id.spinner)
//        binding.btnSignUpNow.setOnClickListener {
//            val bundle = Bundle().apply {
//                putString("name",binding.edtName.text.toString())
//                putString("email",binding.edtEmail.text.toString())
//                putString("password",binding.edtPassword.text.toString())
//            }
//            findNavController().navigate(R.id.action_signUpFragment_to_addressFragment,bundle)
//        }
    }
}