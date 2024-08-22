package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.adapter.HeaderHomeAdapter
import com.example.myapplication.adapter.MyViewPagerAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.model.User
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var user: User? = null
    private val slideHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable: Runnable = object : Runnable { override fun run() {
            val currentItem = binding.viewPage2.currentItem
            val nextItem = if (currentItem == binding.viewPage2.adapter?.itemCount?.minus(1)) {
                0
            } else {
                currentItem + 1
            }
            binding.viewPage2.setCurrentItem(nextItem, true)
            slideHandler.postDelayed(this, 3000)
        }
    }
    private lateinit var authViewModel: AuthViewModel
    private lateinit var foodViewModel: FoodViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var headerHomeAdapter: HeaderHomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        authViewModel = (activity as MainActivity).authViewModel
        foodViewModel = (activity as MainActivity).foodViewModel

        authViewModel.userDetail.observe(viewLifecycleOwner) { data ->
            data?.let {
                user=authViewModel.userDetail.value
                loadImage(user?.imageUrl)
            }
        }


        setupHomeRecycler()
        observeViewModel()
        initAdapter()
        setupTabLayoutAndViewPager()
        setUpViewPager()
    }

    //Theo dõi trạng thái load món ăn
    private fun observeViewModel() {
        foodViewModel.listFoodLiveData.observe(viewLifecycleOwner, Observer { foodList ->
            headerHomeAdapter.differ.submitList(foodList)
        })

        foodViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Thiết lập hiển thị món ăn theo từng loại mục
    private fun setupTabLayoutAndViewPager() {
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager2: ViewPager2 = binding.viewPager

        val fragments = listOf(
            NewTasteFragment(),
            PopularFragment(),
            RecommendedFragment()
        )
        val fragmentTitles = listOf(
            "New Taste",
            "Popular",
            "Recommended"
        )
        val myViewPagerAdapter = MyViewPagerAdapter(requireActivity(), fragments, fragmentTitles)
        viewPager2.adapter = myViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = myViewPagerAdapter.getPageTitle(position)
        }.attach()
    }

    //Thiết lập hiển thị cho viewpager
    private fun setupHomeRecycler() {
        headerHomeAdapter = HeaderHomeAdapter(binding.viewPage2)
        binding.viewPage2.adapter = headerHomeAdapter
    }

    //Thiết lập hiển thị các món ăn trên đầu trang home
    private fun setUpViewPager() {
        binding.viewPage2.offscreenPageLimit = 3
        binding.viewPage2.clipChildren = false
        binding.viewPage2.clipToPadding = false
        (binding.viewPage2.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(50))

        compositePageTransformer.addTransformer { page, position ->
            val scaleFactor = 1 - Math.abs(position)
            page.scaleY = ((0.85 + scaleFactor *0.14f).toFloat())
        }

        binding.viewPage2.setPageTransformer(compositePageTransformer)
        binding.viewPage2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                slideHandler.removeCallbacks(sliderRunnable)
                slideHandler.postDelayed(sliderRunnable, 2000)
            }
        })
    }

    //Khởi tạo adapter cho viewpager
    private fun initAdapter() {
        headerHomeAdapter.setOnItemClickListener {
            foodViewModel.setCount(1)
            val bundle = Bundle().apply {
                putSerializable("food", it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_foodDetailFragment2,bundle)
        }

    }

    //Load ảnh từ server cho avater user

    fun loadImage(imageUrl: String?) {
        val baseUrl = "http://10.0.2.2:8081" // Base URL của server bạn
        val fullImageUrl = imageUrl?.let { "$baseUrl$it" } // Đảm bảo không thêm '/' trùng lặp

        if (fullImageUrl.isNullOrEmpty()) {
            binding.imgUser.setImageResource(R.drawable.image_newspaper)
        } else {
            Glide.with(this)
                .load(fullImageUrl)
                .placeholder(R.drawable.image_newspaper) // Hình ảnh dự phòng khi chưa tải xong
                .error(R.drawable.image_newspaper) // Hình ảnh hiển thị khi tải bị lỗi
                .into(binding.imgUser)
        }
    }


    //

    override fun onResume() {
        super.onResume()
        slideHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        slideHandler.removeCallbacks(sliderRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
