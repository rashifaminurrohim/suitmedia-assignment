package com.assignment.suitmedia.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.suitmedia.databinding.LoadingItemBinding


class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = LoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadingStateViewHolder(private val binding: LoadingItemBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                binding.progressBar.isVisible = true
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
                binding.endOfListMsg.isVisible = false
            } else if (loadState is LoadState.Error) {
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = true
                binding.errorMsg.isVisible = true
                binding.endOfListMsg.isVisible = false
                binding.errorMsg.text = loadState.error.localizedMessage
            } else if (loadState is LoadState.NotLoading) {
                val endOfPaginationReached = loadState.endOfPaginationReached
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
                binding.endOfListMsg.isVisible = endOfPaginationReached
            }
        }
    }
}
