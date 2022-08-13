package com.example.pullrequests.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullrequests.R
import com.example.pullrequests.databinding.RepoItemsBinding
import com.example.pullrequests.model.GithubReposResponse
import com.example.pullrequests.utils.AppUtils
import java.text.SimpleDateFormat

class RepoItemsViewHolder(
    itemView: View,
    private val binding: RepoItemsBinding
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: GithubReposResponse?) {
        binding.apply {
            item?.let {item->

                item.closed_at?.let { closedDate->
                    val dateClosedAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", AppUtils.locale).parse(closedDate)
                    val daySdf = SimpleDateFormat("MMM dd", AppUtils.locale)
                    val dateString = daySdf.format(AppUtils.renderDateToIST(dateClosedAt))
                    val timeSdf = SimpleDateFormat("hh:mm a", AppUtils.locale)
                    val timeString = timeSdf.format(AppUtils.renderDateToIST(dateClosedAt))
                    val dateTimeString = "${dateString}, $timeString"
                    txtClosedAt.text = itemView.context.getString(R.string.closed_at, dateTimeString)
                }

                item.created_at?.let { createdDate->
                    val dateClosedAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", AppUtils.locale).parse(createdDate)
                    val daySdf = SimpleDateFormat("MMM dd", AppUtils.locale)
                    val dateString = daySdf.format(AppUtils.renderDateToIST(dateClosedAt))
                    val timeSdf = SimpleDateFormat("hh:mm a", AppUtils.locale)
                    val timeString = timeSdf.format(AppUtils.renderDateToIST(dateClosedAt))
                    val dateTimeString = "${dateString}, $timeString"
                    txtCreatedAt.text = itemView.context.getString(R.string.created_at, dateTimeString)
                }

                txtUsername.text = item.user?.login
                txtTitle.text = item.title

                Glide.with(itemView.context).load(item.user?.avatar_url).circleCrop().into(imgUser)
            }
        }
    }
}