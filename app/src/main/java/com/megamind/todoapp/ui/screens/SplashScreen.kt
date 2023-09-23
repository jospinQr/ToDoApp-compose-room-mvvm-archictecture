package com.megamind.todoapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
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


   val animatedState = remember{ MutableTransitionState(false).apply {
       targetState=true
   } }
    val density = LocalDensity.current


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



        AnimatedVisibility(
            visibleState = animatedState,
            enter = slideInVertically ()+fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()

        ){
        Text(
            text = "Bienvenu sur ToDoApp",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier


        )}

    }


}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun HomeScreenPreview() {

    ToDoAppTheme {
        SplashScreen()
    }


}