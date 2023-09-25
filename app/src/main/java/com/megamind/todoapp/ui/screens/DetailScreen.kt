package com.megamind.todoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.megamind.todoapp.R
import com.megamind.todoapp.models.Task
import com.megamind.todoapp.ui.theme.ToDoAppTheme
import com.megamind.todoapp.viewModels.TaskViewModel
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    id: String,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: TaskViewModel = viewModel()
) {

    val task by viewModel.fetchTaskById(id.toInt()).observeAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Surface {

        Scaffold(
            topBar = {

                TopAppBar(
                    title = { task?.let { Text(text = it.title) } },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Rounded.ArrowBackIosNew,
                                contentDescription = "iosIcon"
                            )

                        }
                    })
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = {

                Column(
                    modifier = modifier
                        .padding(66.dp)
                        .fillMaxSize()
                ) {

                    task?.let { DetailBox(task = it) }
                    Spacer(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(it)
                    )
                    ActionButtons(
                        onDelete = {

                            coroutineScope.launch {
                                val deletePremission = snackbarHostState.showSnackbar(
                                    message = "Voulez vous vraiment supprimer?",
                                    actionLabel = "Oui",
                                    duration = SnackbarDuration.Long

                                )

                                when (deletePremission) {
                                    Dismissed -> {
                                    }

                                    ActionPerformed -> {
                                        task?.let { viewModel.deleteTask(it) }
                                        navController.popBackStack()
                                    }

                                }


                            }


                        },
                        onEdit = {

                            coroutineScope.launch {
                                val editPermission = snackbarHostState.showSnackbar(

                                    message = "voulez-vous modifier?",
                                    actionLabel = "oui",
                                    duration = SnackbarDuration.Long
                                )
                                when (editPermission) {
                                    Dismissed -> {}
                                    ActionPerformed -> {

                                        task?.let { it1 -> viewModel.editTaskDone(true, it1.id) }
                                    }
                                }
                            }


                        }
                    )


                }
            }

        )
    }
}

@Composable
fun DetailBox(modifier: Modifier = Modifier, task: Task) {

    var isDone by remember { mutableStateOf(false) }

    if (task.done == true) {
        isDone = true
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {

        Column(modifier = modifier.padding(all = 12.0.dp)) {


            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Image(
                    painter = painterResource(R.drawable.routine),
                    contentDescription = "taskImage",
                    modifier = modifier
                        .clip(CircleShape)
                        .size(40.dp)
                )

                Text(
                    text = task.date.toString(),
                    style = MaterialTheme.typography.titleMedium,

                )
            }

            Spacer(modifier = modifier.height(12.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.background,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Checkbox(checked = isDone, onCheckedChange = {})
            }

            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {}
) {
    Row(horizontalArrangement = Arrangement.End, modifier = modifier.fillMaxWidth()) {
        ElevatedButton(modifier = modifier.width(150.dp), onClick = { onEdit() }) {

            Text("Edit")
        }
        Spacer(modifier = modifier.width(16.dp))
        ElevatedButton(modifier = modifier.width(150.dp), onClick = { onDelete() }) {
            Text("Supprimer", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview()
@Composable
fun DetailScreenPreview() {

    ToDoAppTheme {

        val task = Task(
            1,
            "First First  First ",
            Date(),
            "Descripti Descripti Descripti Descripti Descripti Descripti Descripti DescriptiDescriptiDescriptiDescriptiDescriptiDescripti DescriptiDescripti Descripti Descripti Descripti Descripti Descripti Descripti Descripti Descripti Descripti Descripti Descripti ",
            false
        )
        DetailBox(task = task)

    }

}

@Preview()
@Composable
fun ActionButtonPreview() {

    ToDoAppTheme {


        ActionButtons()

    }

}






