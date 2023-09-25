package com.megamind.todoapp.ui

import androidx.annotation.StringRes
import com.megamind.todoapp.R

enum class ToDoAppScreen(@StringRes val title: Int) {
    splashScreen(title = R.string.home),
    todoList(title = R.string.todoList),
    settingsScreen(title = R.string.settings),
    aboutScreen(title = R.string.about),
    detailScreen(title= R.string.detail),
    loginScreen(title = R.string.login),
    signInScreen(title = R.string.singIn)
}






