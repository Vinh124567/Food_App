package com.example.myapplication.View.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.adapter.PopularAdapter
import com.example.myapplication.databinding.FragmentPopularBinding


class NewTasteFragment : Fragment(R.layout.fragment_new_taste) {

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var foodViewModel: FoodViewModel
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularBinding.bind(view)
        foodViewModel = (activity as MainActivity).foodViewModel
        setupHomeRecycler()
        observeViewModel()
    }

    private fun observeViewModel() {
        foodViewModel.listFoodLiveData.observe(viewLifecycleOwner, Observer { foodList ->
            popularAdapter.differ.submitList(foodList)
        })
        foodViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupHomeRecycler() {
        popularAdapter = PopularAdapter()
        binding.recyclerViewPopular.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.recyclerViewPopular.setPadding(0, 0, 0, 0)
        }
    }

}