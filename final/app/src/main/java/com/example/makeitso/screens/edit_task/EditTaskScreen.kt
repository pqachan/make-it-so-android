package com.example.makeitso.screens.edit_task

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.Routes
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.ext.card
import com.example.makeitso.common.ext.fieldModifier
import com.example.makeitso.common.ext.spacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Task
import com.example.makeitso.screens.main.MainScreenViewModel
import com.example.makeitso.theme.MakeItSoTheme
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@Composable
@ExperimentalMaterialApi
fun EditTaskScreen(
  popUpScreen: () -> Unit,
  viewModel: EditTaskViewModel = hiltViewModel(),
  menuviewModel: MainScreenViewModel = hiltViewModel(),
  openScreen: (String) -> Unit
) {
  val task by viewModel.task
  val activity = LocalContext.current as AppCompatActivity
  val selectedIndex = remember { mutableIntStateOf(0) }

  Scaffold(
    bottomBar = {
      com.example.makeitso.screens.main.BottomBar(selectedIndex.value) { index ->
        selectedIndex.value = index
        when (index) {
          0 -> openScreen(Routes.TASKS_SCREEN)
          1 -> openScreen(Routes.QUOTES_SCREEN)
          2 -> openScreen(Routes.STATS_SCREEN)
        }
      }
    }
  ) { innerPadding ->
    EditTaskScreenContent(
      modifier = Modifier.padding(innerPadding),
      task = task,
      onDoneClick = { viewModel.onDoneClick(popUpScreen) },
      onTitleChange = viewModel::onTitleChange,
      onDescriptionChange = viewModel::onDescriptionChange,
      onDateChange = viewModel::onDateChange,
      onTimeChange = viewModel::onTimeChange,
      openMainScreen = { menuviewModel.openMainScreen(openScreen) },
      openStatsScreen = { menuviewModel.openStatsScreen(openScreen) },
      openQuotesScreen = { menuviewModel.openQuotesScreen(openScreen) },
      activity = activity
    )
  }
}

@Composable
@ExperimentalMaterialApi
fun EditTaskScreenContent(
  modifier: Modifier = Modifier,
  task: Task,
  onDoneClick: () -> Unit,
  onTitleChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit,
  onDateChange: (Long) -> Unit,
  onTimeChange: (Int, Int) -> Unit,
  openMainScreen: () -> Unit,
  openStatsScreen: () -> Unit,
  openQuotesScreen: () -> Unit,
  activity: AppCompatActivity?
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .background(Color(0xFFB2FF59))
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    ActionToolbar(
      title = AppText.edit_task,
      modifier = Modifier.toolbarActions(),
      primaryActionIcon = AppIcon.ic_check,
      primaryAction = { onDoneClick() }
    )

    Spacer(modifier = Modifier.spacer())

    val fieldModifier = Modifier.fieldModifier()
    BasicField(AppText.title, task.title, onTitleChange, fieldModifier)
    BasicField(AppText.description, task.description, onDescriptionChange, fieldModifier)

    Spacer(modifier = Modifier.spacer())
    CardEditors(task, onDateChange, onTimeChange, activity)
    Spacer(modifier = Modifier.spacer())
  }
}

@ExperimentalMaterialApi
@Composable
private fun CardEditors(
  task: Task,
  onDateChange: (Long) -> Unit,
  onTimeChange: (Int, Int) -> Unit,
  activity: AppCompatActivity?
) {
  RegularCardEditor(AppText.date, AppIcon.ic_calendar, task.dueDate, Modifier.card()) {
    showDatePicker(activity, onDateChange)
  }

  RegularCardEditor(AppText.time, AppIcon.ic_clock, task.dueTime, Modifier.card()) {
    showTimePicker(activity, onTimeChange)
  }
}


private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
  val picker = MaterialDatePicker.Builder.datePicker().build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { timeInMillis -> onDateChange(timeInMillis) }
  }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
  val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { onTimeChange(picker.hour, picker.minute) }
  }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun EditTaskScreenPreview() {
  val task = Task(
    title = "Title",
    description = "What happened today .."

  )

  MakeItSoTheme {
    EditTaskScreenContent(
      task = task,
      onDoneClick = { },
      onTitleChange = { },
      onDescriptionChange = { },
      onDateChange = { },
      onTimeChange = { _, _ -> },
      //onPriorityChange = { },
      activity = null,
      openMainScreen = {},
      openStatsScreen = {},
      openQuotesScreen = {}

    )
  }
}
