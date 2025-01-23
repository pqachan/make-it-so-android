package com.example.makeitso.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.makeitso.Routes
import com.example.makeitso.common.composable.TopBar
import com.example.makeitso.screens.main.BottomBar

@Composable
fun ProfileScreen(
    navController: NavController,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    openScreen: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB2FF59)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onProfileClick = { openScreen(Routes.TASKS_SCREEN) }
                )
            },
            bottomBar = {
                BottomBar(selectedIndex = 0) { index ->
                    when (index) {
                        0 -> openScreen(Routes.TASKS_SCREEN)
                        1 -> openScreen(Routes.QUOTES_SCREEN)
                        2 -> openScreen(Routes.STATS_SCREEN)
                    }
                }
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        color = Color.Black
                    ) {}

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Aliah",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Edit Profile",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    val options = listOf(
                        "Badges",
                        "Edit Personalized Palette",
                        "Mood Chart",
                        "Summary",
                        "Edit Moods",
                        "Change Password"
                    )

                    options.forEach { option ->
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 16.dp)
                                .background(Color.White, shape = MaterialTheme.shapes.medium)
                                .padding(16.dp)
                                .clickable {
                                    if (option == "Badges") {
                                        navController.navigate("badges")
                                    }
                                    else if (option == "Edit Personalized Palette") {

                                    }
                                    else if (option == "Mood Chart") {

                                    }
                                    else if (option == "Summary") {

                                    }
                                    else if (option == "Edit Moods") {

                                    }
                                    else if (option == "Change Password") {

                                    }
                                }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onLogoutClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                    ) {
                        Text(text = "Logout", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController(), onLogoutClick = {}, openScreen = {}, onProfileClick = {})
}
