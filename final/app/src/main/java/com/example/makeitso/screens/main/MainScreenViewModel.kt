    package com.example.makeitso.screens.main

    import com.example.makeitso.model.service.AccountService
    import com.example.makeitso.model.service.LogService
    import com.example.makeitso.screens.MakeItSoViewModel
    import com.example.makeitso.Routes
    import dagger.hilt.android.lifecycle.HiltViewModel
    import javax.inject.Inject


    @HiltViewModel
    class MainScreenViewModel @Inject constructor(
        logService: LogService,
        private val accountService: AccountService,
    ) : MakeItSoViewModel(logService) {
        val currentUser = accountService.currentUser

        fun openTasksScreen(openScreen: (String) -> Unit) {
            openScreen(Routes.TASKS_SCREEN)
        }

        fun openStatsScreen(openScreen: (String) -> Unit) {
            openScreen(Routes.STATS_SCREEN)
        }

        fun openQuotesScreen(openScreen: (String) -> Unit) {
            openScreen(Routes.QUOTES_SCREEN)
        }

    //    fun openWorkoutGuide(openScreen: (String) -> Unit) {
    //        openScreen(MakeItSoRoutes.WORKOUT_GUIDE)
    //    }
    //
    //    fun openDailyWaterIntake(openScreen: (String) -> Unit) {
    //        openScreen(MakeItSoRoutes.WATER_INTAKE)
    //    }
    //
    //    fun openStepCounter(openScreen: (String) -> Unit) {
    //        openScreen(Routes.STEP_COUNTER)
    //    }
    //
    //    fun openNutriGo(openScreen: (String) -> Unit) {
    //        openScreen(Routes.NUTRI_GO)
    //    }
    //
    //    fun openActivityLog(openScreen: (String) -> Unit) {
    //        openScreen(Routes.ACTIVITY_LOG)
    //    }

        fun onLogoutClick(clearAndNavigate: (String) -> Unit) {
            launchCatching {
                accountService.signOut()
                clearAndNavigate(Routes.LOGIN_SCREEN)
            }
        }
    }