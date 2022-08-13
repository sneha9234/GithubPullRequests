package com.example.pullrequests.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.coremodule.manager.TokenManager
import com.example.coremodule.utils.debouncedOnClick
import com.example.pullrequests.BuildConfig
import com.example.pullrequests.basefiles.BaseActivity
import com.example.pullrequests.model.AccessRequest
import com.example.pullrequests.utils.GithubConstants
import com.example.pullrequests.viewmodel.MainActivityViewModel
import com.example.networkmodule.core.ViewState
import com.example.pullrequests.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by viewModels()
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initObserver()
    }

    private fun initObserver() {
        viewModel.accessTokenResponse.observe(this){res->
            when(res){
                is ViewState.Data -> {
                    res.data.let {
                        Log.d("accessTokenResponse", res.data.toString())
                        tokenManager = TokenManager(this@MainActivity)
                        res.data.access_token?.let { accessToken -> tokenManager.setAuthToken(accessToken) }
                        val intent = Intent(this, GithubActivity::class.java)
                        startActivity(intent)
                    }
                    dismissLoading()
                }
                is ViewState.Error -> {
                    showError(content = res.error)
                    dismissLoading()
                }
                ViewState.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun initListener() {
        binding.btnLogin.debouncedOnClick {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${GithubConstants.LOGIN_URL}?client_id=${BuildConfig.GITHUB_CLIENT_ID}&scope=repo&redirect_uri=${BuildConfig.REDIRECT_URI}"))
            startActivity(intent)
        }
    }

    override fun onResume() {
        if(intent.data!=null && intent.data.toString().startsWith(BuildConfig.REDIRECT_URI)){
            val accessRequest = AccessRequest(
                client_id = BuildConfig.GITHUB_CLIENT_ID,
                client_secret = BuildConfig.GITHUB_CLIENT_SECRET,
                code = intent.data?.getQueryParameter("code")
            )
            viewModel.getAccessToken(accessRequest)
            intent.data = null
        }
        super.onResume()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}