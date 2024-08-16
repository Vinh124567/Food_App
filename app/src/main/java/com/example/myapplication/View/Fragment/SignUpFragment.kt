package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        authViewModel = (activity as MainActivity).authViewModel

        binding.btnContinue.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            if (validateInput(email, password,name)) {
                authViewModel.signUpWithEmail(email, password)
            }
        }

        authViewModel.authResult.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                findNavController().navigate(R.id.action_signUpFragment_to_addressFragment)
            }
        })
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, "Authentication Failed: $it", Toast.LENGTH_SHORT).show()
                authViewModel.authError.value = null
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment2)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        authViewModel.authResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                        findNavController().navigate(R.id.action_loginFragment_to_addressFragment)
                }
            }
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, "Authentication Failed: $it", Toast.LENGTH_SHORT).show()
                authViewModel.authError.value = null
            }
        }
    }

    private fun validateInput(email: String, password: String, name:String): Boolean {
        if (email.isEmpty() || password.isEmpty()||name.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đur thông tin", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(requireContext(), "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}