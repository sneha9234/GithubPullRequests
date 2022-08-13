package com.example.pullrequests.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pullrequests.data.GithubRepository
import com.example.pullrequests.data.UrlManager
import com.example.pullrequests.model.GithubReposResponse
import com.example.networkmodule.core.ViewState
import kotlinx.coroutines.launch
import com.example.networkmodule.core.Result

class GithubViewModel (application: Application) : AndroidViewModel(application) {

    private val repos by lazy { GithubRepository() }

    private val _fetchData = MutableLiveData<Boolean>()
    fun getListofPullRequests(owner:String, repo:String) = _fetchData.switchMap {
        repos.pullRequests(owner, repo)
    }

    fun fetchPullRequests(){
        _fetchData.postValue(true)
    }


}