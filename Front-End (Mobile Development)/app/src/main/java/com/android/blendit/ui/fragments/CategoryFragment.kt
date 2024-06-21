package com.android.blendit.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.blendit.databinding.FragmentCategoryBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.ui.activity.adapter.CategoryAdapter
import com.android.blendit.ui.activity.category.CategoryViewModel
import com.android.blendit.remote.Result
import com.android.blendit.ui.activity.category.CategoryTutorialActivity
import com.android.blendit.viewmodel.ViewModelFactory

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val accountPreference by lazy { AccountPreference(requireActivity()) }
    private val categoryViewModel by activityViewModels<CategoryViewModel> {
        ViewModelFactory.getInstance(accountPreference)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginResult = AccountPreference(requireContext()).getLoginInfo()
        val token = loginResult.token

        categoryViewModel.getCategory(token.toString()).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    // Show loading indicator if needed
                }
                is Result.Success -> {
                    val categories = result.data.listCategory
                    val adapter = CategoryAdapter(categories) { category ->
                        val intent = Intent(requireContext(), CategoryTutorialActivity::class.java).apply {
                            putExtra("CATEGORY_ID", category.id)
                        }
                        startActivity(intent)
                    }
                    binding.rvCategory.adapter = adapter
                    binding.rvCategory.layoutManager = GridLayoutManager(requireContext(), 2)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.data, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}