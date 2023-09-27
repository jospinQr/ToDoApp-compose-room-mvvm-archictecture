package com.megamind.todoapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PanoramaPhotosphereSelect
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.megamind.todoapp.R
import com.megamind.todoapp.models.User
import com.megamind.todoapp.ui.ToDoAppScreen
import com.megamind.todoapp.ui.components.MyAppBar
import com.megamind.todoapp.ui.theme.ToDoAppTheme
import com.megamind.todoapp.viewModels.SignInViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(

    navController: NavController = rememberNavController(),
    viewModel: SignInViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var userName by rememberSaveable { mutableStateOf("") }
    var userPassWord by rememberSaveable { mutableStateOf("") }
    var userPassRepet by rememberSaveable {
        mutableStateOf("")
    }


    var passwordVisible by remember { mutableStateOf(false) }

    Surface {


        Scaffold(

            topBar = {
                MyAppBar(
                    currentScreen = ToDoAppScreen.signInScreen,
                    canNavigate = true,
                    navogateUp = { navController.popBackStack() },
                    onSttings = { /*TODO*/ },
                    OnAppropos = { /*TODO*/ })
            },

            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.background,


            ) {


            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                    Text(

                        modifier = modifier.animateContentSize { initialValue, targetValue -> },
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary,

                            )
                    )

                }


                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text(stringResource(id = R.string.userName)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Decimal
                    )


                )

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(stringResource(id = R.string.firstName)) },

                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )


                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(stringResource(id = R.string.lastName)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )

                )
                var isPass by rememberSaveable {
                    mutableStateOf(true)
                }
                OutlinedTextField(
                    value = userPassWord,
                    onValueChange = { userPassWord = it },
                    label = { Text(stringResource(id = R.string.passWord)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {

                        Icon(
                            imageVector = if (isPass) Icons.Filled.RemoveRedEye else Icons.Filled.PanoramaPhotosphereSelect,
                            contentDescription = null,
                            modifier = modifier.clickable {
                                isPass = !isPass
                            }
                        )


                    },


                    )


                OutlinedTextField(
                    value = userPassRepet,
                    onValueChange = { userPassRepet = it },
                    label = { Text(stringResource(id = R.string.userName)) },

                    trailingIcon = {
                        IconButton(onClick = { passwordVisible=!passwordVisible}) {
                            Icon(imageVector = Icons.Filled.RemoveRedEye, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )
                OutlinedButton(
                    onClick = {
                        val user = User(
                            userName = userName,
                            firstName = firstName,
                            lastName = lastName,
                            passWord = userPassWord
                        )

                        viewModel.singin(user)
                        Toast.makeText(context, "Enregitrement reussit", Toast.LENGTH_SHORT).show()

                    },


                    ) {
                    Text("S'inscrire")
                }

                Spacer(modifier = modifier.height(10.dp))

                TextButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(ToDoAppScreen.loginScreen.name)
                }) {
                    Text("Se connecter")
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun SinginPreview() {

    ToDoAppTheme {

        val user = User(1, "jospin_joe", "jospin", "kahereni", "*452")
        SignInScreen()
    }
}