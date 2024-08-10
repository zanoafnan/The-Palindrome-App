package com.zanoafnan.palindromeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zanoafnan.palindromeapp.databinding.ActivityThirdBinding
import com.zanoafnan.palindromeapp.response.DataItem

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private val userViewModel by viewModels<UserViewModel>()
    private val adapter = UserAdapter()

    private lateinit var tvEmptyMessage: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val back = findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        binding.rvUser.adapter = adapter

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            userViewModel.refreshData()
        }

        userViewModel.listUser.observe(this) { userData ->
            setUserData(userData)
        }

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userViewModel.isRefreshing.observe(this) {
            swipeRefreshLayout.isRefreshing = it
        }

        userViewModel.getUser()

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!userViewModel.isLoading.value!! && !userViewModel.isLastPage &&
                    lastVisibleItem >= totalItemCount - 1
                ) {
                    userViewModel.getUser()
                }
            }
        })

    }

    private fun setUserData(userData: List<DataItem>) {
        if (userData.isEmpty()) {
            tvEmptyMessage.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            tvEmptyMessage.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
            adapter.submitList(userData)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
