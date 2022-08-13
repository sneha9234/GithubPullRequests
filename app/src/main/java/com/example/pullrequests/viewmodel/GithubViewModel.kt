package com.example.pullrequests.viewmodel

import androidx.lifecycle.*
import com.example.pullrequests.data.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(private val repository: GithubRepository) : ViewModel(){

    private val _fetchData = MutableLiveData<Boolean>()
    fun getListofPullRequests(owner:String, repo:String) = _fetchData.switchMap {
            repository.pullRequests(owner, repo)
    }

    fun fetchPullRequests(){
        _fetchData.postValue(true)
    }

}