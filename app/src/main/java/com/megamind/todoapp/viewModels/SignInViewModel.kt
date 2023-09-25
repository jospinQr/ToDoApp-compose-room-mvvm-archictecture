package com.megamind.todoapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.megamind.todoapp.models.User
import com.megamind.todoapp.models.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    val userRepository = UserRepository(application)

    fun singin(user: User) {

        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

}