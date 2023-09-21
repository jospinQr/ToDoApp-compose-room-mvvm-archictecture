package com.megamind.todoapp.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RoundaboutRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.megamind.todoapp.ui.ToDoAppScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    currentScreen: ToDoAppScreen,
    canNavigate: Boolean,
    navogateUp: () -> Unit,
    onSttings: () -> Unit,
    OnAppropos: () -> Unit,
    modifier: Modifier = Modifier

) {

    TopAppBar(title = { Text(text = stringResource(id = currentScreen.title)) },
        navigationIcon = {
            if (canNavigate) {
                IconButton(onClick = { navogateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
            .shadow(elevation = 0.1.dp)
            .heightIn(min = 56.dp),
        actions = {
            IconButton(onClick = { onSttings() }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
            }
            IconButton(onClick = { OnAppropos() }) {
                Icon(imageVector = Icons.Filled.RoundaboutRight, contentDescription = null)
            }
        }

    )
}