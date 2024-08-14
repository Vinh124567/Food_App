package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var foodViewModel: FoodViewModel
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var email: String
    private lateinit var password: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        foodViewModel = (activity as MainActivity).foodViewModel
        foodViewModel.initializeManagers(requireContext())

        binding.btnContinue.setOnClickListener {
            email=binding.edtEmail.text.toString()
            password=binding.edtPassword.text.toString()
            foodViewModel.signUpWithEmail(email, password)
        }

        foodViewModel.emailResult.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                findNavController().navigate(R.id.action_signUpFragment_to_addressFragment)
            }
        })
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment2)
        }
    }


}