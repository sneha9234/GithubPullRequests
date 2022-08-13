package com.example.pullrequests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pullrequests.data.GithubRepository
import com.example.pullrequests.model.AccessRequest
import com.example.pullrequests.model.AccessTokenResponse
import com.example.pullrequests.utils.SingleLiveEvent
import com.example.networkmodule.core.Result
import com.example.networkmodule.core.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: GithubRepository) : ViewModel(){

    private val _accessTokenLivedata: SingleLiveEvent<ViewState<AccessTokenResponse>> =
        SingleLiveEvent()
    val accessTokenResponse: LiveData<ViewState<AccessTokenResponse>> = _accessTokenLivedata

    fun getAccessToken(request: AccessRequest) {
        _accessTokenLivedata.value = ViewState.Loading
        viewModelScope.launch {
            when(val githubResponse = repository.accessToken(request)){
                is Result.Error -> {_accessTokenLivedata.postValue(ViewState.Error(githubResponse.error.message))}
                is Result.Success -> {_accessTokenLivedata.postValue(ViewState.Data(githubResponse.data))}
            }
        }
    }
}