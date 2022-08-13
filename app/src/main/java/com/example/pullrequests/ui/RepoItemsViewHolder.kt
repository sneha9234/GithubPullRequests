package com.example.pullrequests.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pullrequests.databinding.RepoItemsBinding
import com.example.pullrequests.model.GithubReposResponse

class RepoItemsViewHolder(
    itemView: View,
    private val binding: RepoItemsBinding
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: GithubReposResponse?) {
        binding.apply {
            item?.let {item->
                txtAuthorName.text = item.title.toString()
            }
        }
    }
}