package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSingUpSuccessBinding


class SingUpSuccessFragment : Fragment(R.layout.fragment_sing_up_success) {
    private var _binding: FragmentSingUpSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSingUpSuccessBinding.bind(view)
        binding.btnFindFood.setOnClickListener {
            findNavController().navigate(R.id.action_singUpSuccessFragment_to_homeFragment2)
        }
    }
}