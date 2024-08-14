package com.example.myapplication.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var foodViewModel: FoodViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        foodViewModel = (activity as MainActivity).foodViewModel
        foodViewModel.initializeManagers(requireContext())

        binding.btnSignIn.setOnClickListener {
            email = binding.edtEmail.text.toString().trim()
            password = binding.edtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                foodViewModel.signInWithEmail(email, password)
            }
        }

        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        foodViewModel.emailResult.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })

        foodViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), "Login failed: $error", Toast.LENGTH_SHORT).show()
        })

        binding.btnGoogle.setOnClickListener {
            foodViewModel.signOut()
            startActivityForResult(foodViewModel.getGoogleSignInIntent(), RC_SIGN_IN)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        foodViewModel.googleSignInResult.observe(viewLifecycleOwner) { result ->
            result?.let { (user, isNewUser) ->
                if (user != null) {
                    if (isNewUser) {
                        // Nếu là người dùng mới, chuyển đến màn hình nhập thông tin cá nhân
                        findNavController().navigate(R.id.action_loginFragment_to_addressFragment)
                    } else {
                        // Nếu là người dùng cũ, chuyển đến màn hình home
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }

        foodViewModel.signInError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, "Authentication Failed: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            foodViewModel.signInWithGoogle(data)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }


}
