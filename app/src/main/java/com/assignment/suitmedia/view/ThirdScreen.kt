package com.assignment.suitmedia.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.suitmedia.Adapter.LoadingStateAdapter
import com.assignment.suitmedia.Adapter.UserAdapter
import com.assignment.suitmedia.R
import com.assignment.suitmedia.databinding.ActivityThirdScreenBinding
import com.assignment.suitmedia.network.DataItem

class thirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding

    private val thirdScreenViewModel by viewModels<ThirdScreenViewModel> {
        ThirdScreenViewModel.ViewModelFactory(this)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        getUserData()
    }

    private fun getUserData() {
        val adapter = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        thirdScreenViewModel.user.observe(this) { pagingData: PagingData<DataItem> ->
            adapter.submitData(lifecycle, pagingData)
            adapter.addLoadStateListener { loadState ->
                val isEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                binding.emptyStateView.emptyState.visibility =
                    if (isEmpty) View.VISIBLE else View.GONE
            }
            adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataItem) {
                    val selectedName = "${data.firstName} ${data.lastName}"
                    with(sharedPreferences.edit()) {
                        putString("selected_name", selectedName)
                        apply()
                    }
                    Toast.makeText(this@thirdScreen, "you choose $selectedName", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        }
    }
}