package com.megamind.todoapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.megamind.todoapp.models.UserRepository

class LoginViewModel(application: Application) :AndroidViewModel(application) {

      val userRepository = UserRepository(application)

       fun login(userName:String,passWord:String) {

            userRepository.login(userName,passWord)
       }


}