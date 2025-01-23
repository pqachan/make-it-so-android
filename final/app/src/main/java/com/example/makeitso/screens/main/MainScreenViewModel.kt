    package com.example.makeitso.screens.main

    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import com.example.makeitso.EDIT_TASK_SCREEN
    import com.example.makeitso.model.service.AccountService
    import com.example.makeitso.model.service.LogService
    import com.example.makeitso.screens.MakeItSoViewModel
    import com.example.makeitso.Routes
    import com.example.makeitso.TASK_ID
    import com.example.makeitso.model.Task
    import com.example.makeitso.model.service.ConfigurationService
    import com.example.makeitso.model.service.StorageService
    import com.example.makeitso.screens.tasks.TaskActionOption
    import dagger.hilt.android.lifecycle.HiltViewModel
    import javax.inject.Inject


    @HiltViewModel
    class MainScreenViewModel @Inject constructor(
        logService: LogService,
        private val accountService: AccountService,
        private val storageService: StorageService,
        private val configurationService: ConfigurationService
        ) : MakeItSoViewModel(logService) {
        val currentUser = accountService.currentUser

        val options = mutableStateOf<List<String>>(listOf())

        val tasks = storageService.tasks


            fun loadTaskOptions() {
                val hasEditOption = configurationService.isShowTaskEditButtonConfig
                options.value = TaskActionOption.getOptions(hasEditOption)
            }

        fun openTasksScreen(openScreen: (String) -> Unit) {
            openScreen(Routes.TASKS_SCREEN)
        }

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

        fun onAddClick(openScreen: (String) -> Unit) {
            openScreen(Routes.EDIT_TASK_SCREEN)
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


        fun onLogoutClick(clearAndNavigate: (String) -> Unit) {
            launchCatching {
                accountService.signOut()
                clearAndNavigate(Routes.LOGIN_SCREEN)
            }
        }
    }