package com.example.md_lab004__databases.presentation.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.md_lab004__databases.presentation.notes.components.NoteItem
import com.example.md_lab004__databases.presentation.util.Screen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes = viewModel.notes.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your notes") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(notes) {
                NoteItem(
                    note = it,
                    modifier = Modifier.clickable {
                        navController.navigate(
                            Screen.AddEditNoteScreen.route +
                                    "?noteId=${it.id}"
                        )
                    },
                    onDeleteClick = {
                        viewModel.deleteNote(it)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}