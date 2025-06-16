package com.example.listsee.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.example.listsee.core.ResultWrapper
import com.example.listsee.network.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel()
{
    private val _loaderState = MutableLiveData<Boolean>()
        val loaderState: LiveData<Boolean>
            get() = _loaderState

        private val _createdUser = MutableLiveData<Boolean>()
        val createdUser: LiveData<Boolean>
            get() = _createdUser

        fun requestRegister(email:String,password:String){
            _loaderState.value = true
            _createdUser.value = false

            viewModelScope.launch {
                when(val result = repository.register(email, password)) {
                        is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _createdUser.value = true
                }
                    is ResultWrapper.Error -> {
                        _loaderState.value = false
                        val errorMessage = result.exception.message
                    }
                }
            }
        }
}