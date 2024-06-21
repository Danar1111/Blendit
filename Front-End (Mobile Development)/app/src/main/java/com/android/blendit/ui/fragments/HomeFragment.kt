package com.android.blendit.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.blendit.adapter.AdapterListProduct
import com.android.blendit.adapter.LoadingStateAdapter
import com.android.blendit.databinding.FragmentHomeBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.Result
import com.android.blendit.ui.activity.main.MainViewModel
import com.android.blendit.ui.activity.search.SearchActivity
import com.android.blendit.viewmodel.ViewModelFactory
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = AdapterListProduct()
    private val accountPreference by lazy { AccountPreference(requireActivity()) }
    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(
            accountPreference
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFullscreen()
        setView()
        fetchListFavorite()
        fetchUnsplashImage()
        setAdapter()
    }

    private fun fetchListFavorite() {
        mainViewModel.getListFavorite().observe(viewLifecycleOwner) { result ->
            if (result is Result.Success) {
                adapter.setList(result.data)
            }
        }
    }

    private fun setView() {
        binding.searchBar.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    SearchActivity::class.java
                )
            )
        }
        mainViewModel.imageList.observe(viewLifecycleOwner) { imageList ->
            binding.imageSlider.setImageList(imageList)
        }
    }

    private fun fetchUnsplashImage() {
        val imageList = mutableListOf<SlideModel>()
        mainViewModel.getUnsplashImage().observe(viewLifecycleOwner) { result ->
            if (result is Result.Success) {
                if (imageList.isEmpty()) {
                    for (i in 0 until minOf(5, result.data.size)) {
                        val imageUrl = result.data[i].urls.regular
                        imageList.add(SlideModel(imageUrl, ScaleTypes.CENTER_CROP))
                    }
                    mainViewModel.setImagelist(imageList)
                }
            }
        }
    }

    private fun setFullscreen() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.appBar.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    private fun setAdapter() {
        binding.recyclerView.adapter =
            adapter.withLoadStateFooter(LoadingStateAdapter { adapter.retry() })
        mainViewModel.getListProduct().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }
}