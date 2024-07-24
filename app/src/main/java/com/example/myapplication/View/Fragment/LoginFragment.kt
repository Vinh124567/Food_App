package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.SharedPreferences.PreferencesManager
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var foodViewModel: FoodViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodViewModel = (activity as MainActivity).foodViewModel
        binding = FragmentLoginBinding.bind(view)
        binding.btnSingIn.setOnClickListener {
            email=binding.edtEmail.text.toString()
            password=binding.edtPassword.text.toString()
            foodViewModel.requestLogin(email,password)
          
        }
        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)

        }

        foodViewModel._loginResult.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                // Lấy id từ đối tượng user
                val userId = it.id
                // Lưu id vào SharedPreferences
                PreferencesManager.getInstance(requireContext()).setId(userId)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })

        foodViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), "Login failed: $error", Toast.LENGTH_SHORT).show()
        })
    }


}