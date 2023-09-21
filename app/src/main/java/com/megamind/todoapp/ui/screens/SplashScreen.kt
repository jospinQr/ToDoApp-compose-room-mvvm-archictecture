package com.megamind.todoapp

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.megamind.todoapp.ui.ToDoAppScreen
import com.megamind.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {


    val scale = remember{ Animatable(3f) }
    val coroutineScope= rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch { scale.animateTo(targetValue = 20f) }
        delay(3000)
        navController.popBackStack()
        navController.navigate(ToDoAppScreen.todoList.name)

    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        Text(
            text = "Bienvenu sur ToDoApp",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.scale(scale=scale.value)

        )




    }


}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun HomeScreenPreview() {

    ToDoAppTheme {
        SplashScreen()
    }


}