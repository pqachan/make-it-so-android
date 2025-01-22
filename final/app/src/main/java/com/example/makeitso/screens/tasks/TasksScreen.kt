
package com.example.makeitso.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Task
import com.example.makeitso.theme.MakeItSoTheme

@Composable
@ExperimentalMaterialApi
fun TasksScreen(
  openScreen: (String) -> Unit,
  viewModel: TasksViewModel = hiltViewModel()
) {
  val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList())
  val options by viewModel.options

  TasksScreenContent(
    tasks = tasks.value,
    options = options,
    onAddClick = viewModel::onAddClick,
    onStatsClick = viewModel::onStatsClick,
    onSettingsClick = viewModel::onSettingsClick,
    onTaskActionClick = viewModel::onTaskActionClick,
    openScreen = openScreen
  )

  LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun TasksScreenContent(
  modifier: Modifier = Modifier,
  tasks: List<Task>,
  options: List<String>,
  onAddClick: ((String) -> Unit) -> Unit,
  onStatsClick: ((String) -> Unit) -> Unit,
  onSettingsClick: ((String) -> Unit) -> Unit,
  onTaskActionClick: ((String) -> Unit, Task, String) -> Unit,
  openScreen: (String) -> Unit
) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { onAddClick(openScreen) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier.padding(16.dp)
      ) {
        Icon(Icons.Filled.Add, "Add")
      }
    }
  ) {
    Column(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()) {
      ActionToolbar(
        title = AppText.tasks,
        modifier = Modifier.toolbarActions(),
        primaryActionIcon = AppIcon.ic_stats,
        primaryAction = { onStatsClick(openScreen) },
        secondaryActionIcon = AppIcon.ic_settings,
        secondaryAction = { onSettingsClick(openScreen) }
      )

      Spacer(modifier = Modifier.smallSpacer())

      LazyColumn {
        items(tasks, key = { it.id }) { taskItem ->
          TaskItem(
            task = taskItem,
            options = options,
            onActionClick = { action -> onTaskActionClick(openScreen, taskItem, action) }
          )
        }
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
      onStatsClick = { },
      onSettingsClick = { },
      onTaskActionClick = { _, _, _ -> },
      openScreen = { }
    )
  }
}
