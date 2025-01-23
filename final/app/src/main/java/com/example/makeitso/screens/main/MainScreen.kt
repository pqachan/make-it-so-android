package com.example.makeitso.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.makeitso.R
import com.example.makeitso.Routes
import com.example.makeitso.model.Task
import com.example.makeitso.screens.tasks.TaskItem
import com.example.makeitso.screens.tasks.TasksViewModel
import com.example.makeitso.theme.MakeItSoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    taskviewModel: TasksViewModel = hiltViewModel(),
    clearAndNavigate: (String) -> Unit,
    openScreen: (String) -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    val tasks = taskviewModel.tasks.collectAsStateWithLifecycle(emptyList())
    val options by taskviewModel.options

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
                    0 -> openScreen(Routes.MAIN_SCREEN)
                    1 -> openScreen(Routes.QUOTES_SCREEN)
                    2 -> openScreen(Routes.STATS_SCREEN)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddClick(openScreen) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Create, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFB2FF59))
                .padding(innerPadding)
        ) {
            MainScreenContent(
                modifier = Modifier.padding(16.dp),
                tasks = tasks.value,
                options = options,
                openTasksScreen = { viewModel.openTasksScreen(openScreen) },
                openStatsScreen = { viewModel.openStatsScreen(openScreen) },
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
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    options: List<String>,
    onTaskActionClick: ((String) -> Unit, Task, String) -> Unit,
    onAddClick: ((String) -> Unit) -> Unit,
    openScreen: (String) -> Unit,
    openTasksScreen: () -> Unit,
    openStatsScreen: () -> Unit,
    openQuotesScreen: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
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

@Composable
fun BottomBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 5.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.quotes), contentDescription = "Quotes") },
            label = { Text("Quotes") },
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.chart), contentDescription = "Stats") },
            label = { Text("Stats") },
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onProfileClick: () -> Unit

) {
    TopAppBar(
        title = { Text("MySelf") },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(painter = painterResource(id = R.drawable.profile), contentDescription = "Profile")
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MakeItSoTheme {
        MainScreen(
            clearAndNavigate = { },
            openScreen = { }
        )
    }
}