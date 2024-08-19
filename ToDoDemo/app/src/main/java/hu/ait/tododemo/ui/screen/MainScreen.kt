package hu.ait.tododemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.tododemo.ui.screen.TodoViewModel

@Composable
fun MainScreen(
    todoViewModel: TodoViewModel = viewModel(),
    onNavigateToSummary: () -> Unit
) {
    var todoTitle by rememberSaveable {
        mutableStateOf("")
    }
    var todoDescription by rememberSaveable {
        mutableStateOf("")
    }
    var todoImportant by rememberSaveable {
        mutableStateOf(false)
    }
    Column {
        OutlinedTextField(
            value = todoTitle,
            onValueChange = {todoTitle=it},
            label = { Text(text = "Enter todo here:") }
        )
        OutlinedTextField(
            value = todoDescription,
            onValueChange = {todoDescription=it},
            label = { Text(text = "Enter todo here:") }
        )

        Row() {
            Checkbox(checked = todoImportant, onCheckedChange = {todoImportant=it})
            Text(text = "Important")
        }
        Row {
            Button(onClick = {

            }) {
                Text(text = "Add")
            }
            Button(onClick = {
                onNavigateToSummary()
            }) {
                Text(text = "Sum")
            }
        }

    }
}