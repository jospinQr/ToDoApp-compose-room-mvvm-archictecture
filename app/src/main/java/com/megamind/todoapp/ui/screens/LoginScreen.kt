package com.megamind.todoapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.megamind.todoapp.R
import com.megamind.todoapp.viewModels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {


    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.padding(28.dp),

        content = {

            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text(stringResource(id = R.string.userName)) },

                    )
                Spacer(modifier = modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(text = stringResource(id = R.string.passWord))
                    },

                    
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = modifier.height(8.dp))
                Button(onClick = {

                    viewModel.login(userName, password)


                }) {
                   Text(text = "Enregistrer")
                }


            }
        })


}