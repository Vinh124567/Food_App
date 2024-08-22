package com.example.myapplication.View.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        authViewModel = (activity as MainActivity).authViewModel

        binding.btnContinue.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            if (validateInput(email, password,name)) {
                val bundle = Bundle().apply {
                    putString("email", email)
                    putString("password", password)
                    putString("name", name)
                    putString("uri",selectedImageUri.toString())
                }
                findNavController().navigate(R.id.action_signUpFragment_to_addressFragment,bundle)
            }
        }

        binding.imgBtnAvatar.setOnClickListener {
            pickImageFromGallery()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment2)
        }

    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                Glide.with(this).load(uri).into(binding.imgBtnAvatar)
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