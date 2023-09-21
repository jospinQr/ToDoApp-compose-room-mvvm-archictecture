package com.megamind.todoapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.megamind.todoapp.R

@Composable
fun MyAlertDialog(
    title: String,
    message: String,
    icon: ImageVector,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        icon = { Icon(imageVector = icon, contentDescription = null) },
        text = { Text(message) },
        title = { Text(text = title) },
        onDismissRequest = { onCancel() },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = stringResource(id = R.string.confimer))
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(text = stringResource(id = R.string.cancle))
            }
        }

    )

}