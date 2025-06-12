package com.example.listsee.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listsee.core.ResultWrapper
import com.example.listsee.model.Task
import com.example.listsee.network.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.Typography.dagger

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _taskInfo = MutableLiveData<List<Task>>()
    val taskInfo: LiveData<List<Task>>
        get() = _taskInfo

    fun fetchAllTasks() {
        _loaderState.value = true

        viewModelScope.launch {
            when (val result = repository.getAllTasks()) {
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
}