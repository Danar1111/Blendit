package com.android.blendit.ui.activity.search

import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.blendit.adapter.AdapterFindListProduct
import com.android.blendit.adapter.LoadingStateAdapter
import com.android.blendit.databinding.ActivitySearchBinding
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.Result
import com.android.blendit.viewmodel.ViewModelFactory

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val accountPreference by lazy { AccountPreference(this) }
    private val adapter = AdapterFindListProduct()
    private val viewmodel by viewModels<SearchViewModel> {
        ViewModelFactory(
            accountPreference
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFullscreen()
        setView()
        fetchListFavorite()
        setAdapter("")
    }

    private fun setView() {
        with(binding) {
            btnBack.setOnClickListener { finish() }
            searchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    fetchListFavorite()
                    setAdapter(p0)
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    fetchListFavorite()
                    setAdapter(p0)
                    return true
                }
            })
        }
    }

    private fun fetchListFavorite() {
        viewmodel.getListFavorite().observe(this) { result ->
            if (result is Result.Success) {
                adapter.setList(result.data)
            }
        }
    }

    private fun setAdapter(query: String?) {
        binding.recyclerView.adapter =
            adapter.withLoadStateFooter(LoadingStateAdapter { adapter.retry() })
        viewmodel.findProduct(query.toString()).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setFullscreen() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}