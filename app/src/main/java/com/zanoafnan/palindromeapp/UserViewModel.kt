package com.zanoafnan.palindromeapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zanoafnan.palindromeapp.api.ApiConfig
import com.zanoafnan.palindromeapp.response.DataItem
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<DataItem>>()
    val listUser: LiveData<List<DataItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private var currentPage = 1
    private val pageSize = 10
    var isLastPage = false

    fun getUser() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().getUsers(currentPage, pageSize)

                _isLoading.value = false
                if (response.isSuccessful) {
                    val newData = response.body()?.data ?: emptyList()
                    _listUser.value = (_listUser.value ?: emptyList()) + newData

                    if (newData.size < pageSize) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                } else {
                    Log.e("userViewModel", "onFailure: ${response.message()}")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e("userViewModel", "onFailure: ${e.message.toString()}")
            }
        }
    }


    fun refreshData() {
        currentPage = 1
        isLastPage = false
        _isRefreshing.value = true
        _listUser.value = emptyList()
        getUser()
        _isRefreshing.value = false
    }
}
