package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        binding.btnContinue.setOnClickListener {
            val bundle = Bundle().apply {
                putString("name",binding.edtName.text.toString())
                putString("email",binding.edtEmail.text.toString())
                putString("password",binding.edtPassword.text.toString())
            }
            findNavController().navigate(R.id.action_signUpFragment_to_addressFragment,bundle)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment2)
        }
    }


}