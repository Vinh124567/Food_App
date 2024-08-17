package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        authViewModel = (activity as MainActivity).authViewModel

        binding.btnContinue.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            if (validateInput(email, password,name)) {
                authViewModel.signUpWithEmail(email, password)
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
                        findNavController().navigate(R.id.action_signUpFragment_to_addressFragment)
                }
            }
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, "Authentication Failed: $it", Toast.LENGTH_SHORT).show()
                authViewModel.authError.value = null
            }
        }
    }

    private fun validateInput(email: String, password: String, name: String): Boolean {
        return when {
            email.isEmpty() || password.isEmpty() || name.isEmpty() -> {
                showToast("Vui lòng nhập đủ thông tin")
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Email không hợp lệ")
                false
            }
            password.length < 6 -> {
                showToast("Mật khẩu phải có ít nhất 6 ký tự")
                false
            }
            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}