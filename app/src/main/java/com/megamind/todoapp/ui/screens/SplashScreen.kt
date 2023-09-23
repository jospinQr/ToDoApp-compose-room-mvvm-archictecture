package com.megamind.todoapp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.megamind.todoapp.ui.ToDoAppScreen
import com.megamind.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {





    LaunchedEffect(Unit) {

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

        val rotation = remember { Animatable(0f) }
        LaunchedEffect(rotation) {
            rotation.animateTo(360f, animationSpec = tween(durationMillis = 1000))
        }
        Box (modifier=modifier.rotate(rotation.value)){
            Text(
                text = "Bienvenu sur ToDoApp",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier


            )
        }

    }


}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun HomeScreenPreview() {

    ToDoAppTheme {
        SplashScreen()
    }


}