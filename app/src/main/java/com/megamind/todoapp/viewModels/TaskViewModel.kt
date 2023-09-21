package com.megamind.todoapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.megamind.todoapp.models.Task
import com.megamind.todoapp.models.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: TaskRepository = TaskRepository(application)


    fun fetchAllTask(): LiveData<List<Task>> {
        return repository.allTask
    }

    fun fetchTaskById(taskId: Int): LiveData<Task> {
        return repository.taskById(taskId)
    }

    fun insertTask(task: Task) = viewModelScope.launch { repository.insertTask(task) }

    fun deleteTask(task: Task) = viewModelScope.launch { repository.deleteTask(task = task) }


}

