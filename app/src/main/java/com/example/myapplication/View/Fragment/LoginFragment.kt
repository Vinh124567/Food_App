package com.example.myapplication.View.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        authViewModel = (activity as MainActivity).authViewModel


        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            if (validateInput(email, password)) {
                authViewModel.signInWithEmail(email, password)
            }
        }

        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnGoogle.setOnClickListener {
            authViewModel.signOut()
            startActivityForResult(authViewModel.getGoogleSignInIntent(), RC_SIGN_IN)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        authViewModel.authResult.observe(viewLifecycleOwner) { result ->
            handleSignInResult(result)
        }
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, "Authentication Failed: $it", Toast.LENGTH_SHORT).show()
                authViewModel.authError.value = null
            }
        }
    }

    private fun handleSignInResult(result: Pair<FirebaseUser?, Boolean?>?) {
        result?.let { (user, isNewUser) ->
            if (user != null) {
                if (isNewUser == true) {
                    findNavController().navigate(R.id.action_loginFragment_to_addressFragment)
                } else {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            authViewModel.signInWithGoogle(data)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
