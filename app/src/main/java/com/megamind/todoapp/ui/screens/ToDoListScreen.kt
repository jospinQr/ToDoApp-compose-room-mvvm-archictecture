package com.megamind.todoapp


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.megamind.todoapp.models.Task
import com.megamind.todoapp.ui.ToDoAppScreen
import com.megamind.todoapp.ui.components.MyAppBar
import com.megamind.todoapp.ui.theme.ToDoAppTheme
import com.megamind.todoapp.viewModels.TaskViewModel
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ToDoListScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: TaskViewModel = viewModel(),
    onItemClicked: (id: String) -> Unit = {},
) {


    var context = LocalContext.current
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen =
        ToDoAppScreen.valueOf(backStackEntry?.destination?.route ?: ToDoAppScreen.splashScreen.name)
    //observer la liste des taches
    val tasks by viewModel.fetchAllTask().observeAsState(arrayListOf())
    //l'etat de l'alert dialog
    var isDialogOpen by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()


    var taskTitle by remember { mutableStateOf("") }
    var taskDescrption by remember { mutableStateOf("") }



    ModalBottomSheetLayout(

        modifier = modifier.clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topEnd = 18.dp, topStart = 18.dp),

        sheetContent = {


            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                    .padding(all = 8.dp)
            ) {


                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
                {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icon")
                }
                Spacer(modifier = modifier.height(20.dp))
                Column(
                    modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        label={Text("Titre")},
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.PendingActions,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)

                    )

                    Spacer(modifier = modifier.height(12.dp))

                    OutlinedTextField(
                        label={ Text("Déscription")},
                        value = taskDescrption,
                        onValueChange = { taskDescrption = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.TaskAlt,
                                contentDescription = null
                            )

                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                    )
                    Spacer(modifier = modifier.height(12.dp))

                    ElevatedButton(
                        modifier = modifier,
                        onClick = {
                            val task = Task(
                                title = taskTitle,
                                date = Date(),
                                description = taskDescrption,
                                done = false
                            )

                            viewModel.insertTask(task)
                            Toast.makeText(
                                context,
                                "Enregistrement réussit",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }) {
                        Text(text = stringResource(id = R.string.save))
                    }

                }


            }
        }
    ) {
        Scaffold(
            topBar = {
                MyAppBar(
                    currentScreen = currentScreen,
                    canNavigate = navController.previousBackStackEntry != null,
                    navogateUp = { /*TODO*/ },
                    onSttings = { /*TODO*/ },
                    OnAppropos = { /*TODO*/ }
                )
            },
            floatingActionButton = {


                FloatingActionButton(

                    onClick = {

//                    val task1 = Task( "Mon premier", Date(), "joe", false);
//                    viewModel.insertTask(task1)
//                    isDialogOpen = true
                        coroutineScope.launch { sheetState.show() }

                    },
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            },
            content = {


                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    color = MaterialTheme.colorScheme.background
                ) {


                    if (tasks.isEmpty()) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Liste vide")
                        }
                    } else {
                        LazyColumn(modifier.padding(4.dp)) {
                            items(items = tasks) { task ->
                                TaskItem(
                                    id = task.id,
                                    title = task.title,
                                    date = task.date!!,
                                    description = task.description,
                                    onclick = {
                                        onItemClicked(task.id.toString())
                                    }
                                )
                            }
                        }
                    }

                }


            }
        )
    }

}


@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    date: Date,
    description: String,
    onclick: () -> Unit = {},

    ) {

    var isDev by rememberSaveable {
        mutableStateOf(false)
    }
    val extratPading by animateDpAsState(
        if (isDev) 50.dp else 0.dp, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onclick() }
    ) {
        Row(
            modifier = modifier
                .padding(25.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = extratPading.coerceAtLeast(0.dp))

            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$id",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                    )
                    Text(
                        text = dateToString(date),
                        modifier = modifier,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = title,
                    modifier = modifier,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)

                )
                if (isDev) {
                    Text(
                        text = description,
                        modifier = modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal)

                    )
                }

            }
            IconButton(onClick = { isDev = !isDev }) {
                Icon(
                    imageVector = if (isDev) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (isDev) {
                        "dev"
                    } else {
                        "Not yet"
                    }
                )
            }


        }
    }


}

@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        TaskItem(
            id = 1,
            title = "My Fiste",
            description = "Salut les gars",
            date = Date(),
        )
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreviewDark() {
    ToDoAppTheme {
        TaskItem(
            id = 1,
            title = "My First",
            description = "Salut les gars",
            date = Date(),
        )
    }
}