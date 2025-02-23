/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.screens.tasks

import androidx.compose.runtime.mutableStateOf
import com.example.makeitso.EDIT_TASK_SCREEN
import com.example.makeitso.Routes
import com.example.makeitso.SETTINGS_SCREEN
import com.example.makeitso.STATS_SCREEN
import com.example.makeitso.TASK_ID
import com.example.makeitso.model.Task
import com.example.makeitso.model.service.ConfigurationService
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : MakeItSoViewModel(logService) {
  val options = mutableStateOf<List<String>>(listOf())

  val tasks = storageService.tasks

  fun loadTaskOptions() {
    val hasEditOption = configurationService.isShowTaskEditButtonConfig
    options.value = TaskActionOption.getOptions(hasEditOption)
  }

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

  fun onProfileClick(openScreen: (String) -> Unit) {
    openScreen(Routes.PROFILE_SCREEN)
  }

  fun openProfileScreen(openScreen: (String) -> Unit) {
    openScreen(Routes.PROFILE_SCREEN)
  }

  fun openMainScreen(openScreen: (String) -> Unit) {
    openScreen(Routes.MAIN_SCREEN)
  }

  fun openStatsScreen(openScreen: (String) -> Unit) {
    openScreen(Routes.STATS_SCREEN)
  }

  fun openQuotesScreen(openScreen: (String) -> Unit) {
    openScreen(Routes.QUOTES_SCREEN)
  }

  fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
    when (TaskActionOption.getByTitle(action)) {
      TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
      TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
    }
  }


  private fun onDeleteTaskClick(task: Task) {
    launchCatching { storageService.delete(task.id) }
  }
}
