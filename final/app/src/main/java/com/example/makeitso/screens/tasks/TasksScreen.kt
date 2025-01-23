
package com.example.makeitso.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.makeitso.Routes
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Task
import com.example.makeitso.screens.main.BottomBar
import com.example.makeitso.screens.main.MainScreenContent
import com.example.makeitso.screens.main.TopBar
import com.example.makeitso.theme.MakeItSoTheme

@Composable
@ExperimentalMaterialApi
fun TasksScreen(
  openScreen: (String) -> Unit,
  viewModel: TasksViewModel = hiltViewModel()
) {
  val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
  val options by viewModel.options

  val selectedIndex = remember { mutableIntStateOf(0) }

  Scaffold(
    topBar = {
      TopBar(
        onProfileClick = { viewModel.onProfileClick(openScreen) }
      )
    },
    bottomBar = {
      BottomBar(selectedIndex.value) { index ->
        selectedIndex.value = index
        when (index) {
          0 -> openScreen(Routes.TASKS_SCREEN)
          1 -> openScreen(Routes.QUOTES_SCREEN)
          2 -> openScreen(Routes.STATS_SCREEN)
        }
      }
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { viewModel.onAddClick(openScreen) },
        backgroundColor = MaterialTheme.colors.primary
      ) {
        Icon(Icons.Default.Add, contentDescription = "Add Task")
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFB2FF59))
        .padding(innerPadding)
    ) {
      TasksScreenContent(
        modifier = Modifier.padding(16.dp),
        tasks = tasks.value,
        options = options,
        openQuotesScreen = { viewModel.openQuotesScreen(openScreen) },
        onAddClick = { viewModel.onAddClick(openScreen) },
        onTaskActionClick = viewModel::onTaskActionClick,
        onProfileClick = { viewModel.openProfileScreen(openScreen) },
        openScreen = openScreen
      )

      LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
    }
  }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun TasksScreenContent(
  modifier: Modifier = Modifier,
  tasks: List<Task>,
  options: List<String>,
  openQuotesScreen: () -> Unit,
  onProfileClick: () -> Unit,
  onAddClick: ((String) -> Unit) -> Unit,
  onTaskActionClick: ((String) -> Unit, Task, String) -> Unit,
  openScreen: (String) -> Unit
) {

  Column(
    modifier = modifier
      .fillMaxSize()
  ) {
    androidx.compose.material3.Text(
      text = "Welcome to MySelf",
      fontWeight = FontWeight.Bold,
      fontSize = 24.sp,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    LazyColumn {
      items(tasks, key = { it.id }) { taskItem ->
        TaskItem(
          task = taskItem,
          options = options,
          onActionClick = { action -> onTaskActionClick(openScreen, taskItem, action ) }
        )
      }
    }
  }
}



@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun TasksScreenPreview() {
  val task = Task(
    title = "Task title",
    completed = true
  )

  val options = TaskActionOption.getOptions(hasEditOption = true)

  MakeItSoTheme {
    TasksScreenContent(
      tasks = listOf(task),
      options = options,
      onAddClick = { },
      onProfileClick = {},
      openQuotesScreen = {},
      onTaskActionClick = { _, _, _ -> },
      openScreen = { }
    )
  }
}
