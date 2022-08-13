package com.example.pullrequests.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pullrequests.basefiles.BaseActivity
import com.example.pullrequests.databinding.ActivityGithubBinding
import com.example.pullrequests.viewmodel.GithubViewModel
import com.example.networkmodule.core.ViewState

class GithubActivity : BaseActivity(){
    private var _binding: ActivityGithubBinding? = null
    private val binding get() = _binding!!
    private val githubReposAdapter: GithubReposAdapter by lazy {
        GithubReposAdapter()
    }
    private val viewModel: GithubViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        hitApis()
        initObserver()
        initObserver()
    }

    private fun initView() {

        setSupportActionBar(binding.myToolbar)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = githubReposAdapter
    }

    private fun hitApis() {
        viewModel.fetchPullRequests()
    }

    private fun initObserver() {
        viewModel.getListofPullRequests("sneha9234","VTUcalc").observe(this) {
            githubReposAdapter.submitData(lifecycle, it)
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}