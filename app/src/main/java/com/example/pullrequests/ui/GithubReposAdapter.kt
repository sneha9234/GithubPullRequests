package com.example.pullrequests.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.pullrequests.databinding.RepoItemsBinding
import com.example.pullrequests.model.GithubReposResponse
import java.util.*

class GithubReposAdapter: PagingDataAdapter<GithubReposResponse, RepoItemsViewHolder>(diff) {

    lateinit var binding: RepoItemsBinding

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RepoItemsViewHolder {

        binding = RepoItemsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        val view = binding.root
        return RepoItemsViewHolder(view, binding)
    }

    override fun onBindViewHolder(holder: RepoItemsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object diff :
        DiffUtil.ItemCallback<GithubReposResponse>() {
        override fun areItemsTheSame(oldItem: GithubReposResponse, newItem: GithubReposResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GithubReposResponse,
            newItem: GithubReposResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

}