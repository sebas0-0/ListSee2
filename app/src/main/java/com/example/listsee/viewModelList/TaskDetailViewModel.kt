package com.example.listsee.viewModelList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listsee.core.ResultWrapper
import com.example.listsee.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.example.listsee.network.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.Date

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel(){
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _taskInfo = MutableLiveData<Task>()
    val taskInfo: LiveData<Task>
        get() = _taskInfo

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean>
        get() = _operationSuccess


    fun getTaskInfo(id: String) {
        _loaderState.value = true

        viewModelScope.launch {
            when (val result = repository.getTask(id)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _taskInfo.value = result.data
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }
    fun deleteTask(id: String) {
        _operationSuccess.value = false
        _loaderState.value = true

        viewModelScope.launch {
            when (val result = repository.deleteTask(id)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _operationSuccess.value = true
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }

    fun updateTask(id: String, name: String, description: String, date: Date) {
        _operationSuccess.value = false
        val userId = firebaseAuth.currentUser?.uid ?: throw Exception("Usuario no autenticado")
        val task = Task(id = id, name, description, date, userId)
        _loaderState.value = true

        viewModelScope.launch {
            when (val result = repository.updateTask(task)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _operationSuccess.value = true
                }

                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }

}