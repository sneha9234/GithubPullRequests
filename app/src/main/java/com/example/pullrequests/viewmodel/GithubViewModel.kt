package com.example.pullrequests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pullrequests.data.GithubRepository
import com.example.pullrequests.data.UrlManager
import com.example.pullrequests.model.GithubReposResponse
import com.example.networkmodule.core.ViewState
import kotlinx.coroutines.launch
import com.example.networkmodule.core.Result

class GithubViewModel (application: Application) : AndroidViewModel(application) {

    private val repos by lazy { GithubRepository() }
    private val urlManager by lazy { UrlManager() }

    private val _pullRequestsLivedata: MutableLiveData<ViewState<List<GithubReposResponse>>> =
        MutableLiveData<ViewState<List<GithubReposResponse>>>()
    val pullRequestsResponse: LiveData<ViewState<List<GithubReposResponse>>> = _pullRequestsLivedata


    fun getListofPullRequests(owner:String, repo:String) {
        _pullRequestsLivedata.value = ViewState.Loading
        viewModelScope.launch {
            when(val githubResponse = repos.pullRequests(urlManager.getPullRequestsUrl(owner, repo),"closed")){
                is Result.Error -> {_pullRequestsLivedata.postValue(ViewState.Error(githubResponse.error.message))}
                is Result.Success -> {_pullRequestsLivedata.postValue(ViewState.Data(githubResponse.data))}
            }
        }
    }

}