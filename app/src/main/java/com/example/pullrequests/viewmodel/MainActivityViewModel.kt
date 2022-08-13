package com.example.pullrequests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pullrequests.data.GithubRepository
import com.example.pullrequests.model.AccessRequest
import com.example.pullrequests.model.AccessTokenResponse
import com.example.pullrequests.utils.SingleLiveEvent
import com.example.networkmodule.core.Result
import com.example.networkmodule.core.ViewState
import kotlinx.coroutines.launch

class MainActivityViewModel (application: Application) : AndroidViewModel(application) {

    private val repos by lazy { GithubRepository() }

    private val _accessTokenLivedata: SingleLiveEvent<ViewState<AccessTokenResponse>> =
        SingleLiveEvent()
    val accessTokenResponse: LiveData<ViewState<AccessTokenResponse>> = _accessTokenLivedata

    fun getAccessToken(request: AccessRequest) {
        _accessTokenLivedata.value = ViewState.Loading
        viewModelScope.launch {
            when(val githubResponse = repos.accessToken(request)){
                is Result.Error -> {_accessTokenLivedata.postValue(ViewState.Error(githubResponse.error.message))}
                is Result.Success -> {_accessTokenLivedata.postValue(ViewState.Data(githubResponse.data))}
            }
        }
    }
}