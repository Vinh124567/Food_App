package com.example.myapplication.View.Fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.databinding.FragmentAddressBinding
import com.example.myapplication.model.User
import com.google.firebase.auth.FirebaseUser


class AddressFragment : Fragment(R.layout.fragment_address) {
    private val args: AddressFragmentArgs by navArgs()
    private lateinit var userId: String
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var password: String
    private lateinit var phone: String
    private lateinit var address: String
    private lateinit var house: String
    private lateinit var city: String
    private lateinit var binding: FragmentAddressBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var imageUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddressBinding.bind(view)
        authViewModel = (activity as MainActivity).authViewModel
        email = args.email
        password = args.password
        name = args.name
        imageUri=Uri.parse(args.uri)

        val items = listOf("Ninh Bình", "Nam Định", "Hà Nam")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items
                ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Layout cho dropdown
            }
            binding.spinnerAddress.adapter = adapter

            binding.btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_addressFragment_to_signUpFragment)
            }

            binding.btnSignUpNow.setOnClickListener {
                authViewModel.signUpWithEmail(email, password)
            }
            observeViewModel()
        }

    private fun observeViewModel() {
        authViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.paginationProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
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
        result?.let {
            if (it.first != null) {
                userId = it.first!!.uid
                val user = createUser()
                authViewModel.createUser(user,imageUri)
                authViewModel.successLiveData.observe(viewLifecycleOwner) { success ->
                    if (success != null) {
                        findNavController().navigate(R.id.action_addressFragment_to_singUpSuccessFragment)
                    } else {
                        Toast.makeText(requireContext(), "Error saving user", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun createUser(): User{
        house = binding.edtHouse.text.toString()
        phone = binding.edtPhone.text.toString()
        address = binding.edtAddress.text.toString()
        city = binding.spinnerAddress.selectedItem.toString()
        return  User (userId,name, email, address, phone, house,city)
    }





    }