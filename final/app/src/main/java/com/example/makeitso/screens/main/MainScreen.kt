package com.example.makeitso.screens.main

//import android.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.makeitso.R
import com.example.makeitso.model.User
import com.example.makeitso.theme.MakeItSoTheme


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    clearAndNavigate: (String) -> Unit,
    openScreen: (String) -> Unit,
) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    MainScreenContent(
        openTasksScreen = { viewModel.openTasksScreen(openScreen) },
        openStatsScreen = { viewModel.openStatsScreen(openScreen) },
        openQuotesScreen = { viewModel.openQuotesScreen(openScreen) },
        onLogoutClick = { viewModel.onLogoutClick(clearAndNavigate) }
    )
}

@Composable
fun MainScreenContent(
    openTasksScreen: () -> Unit,
    openStatsScreen: () -> Unit,
    openQuotesScreen: () -> Unit,
    onLogoutClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF5FB1B7), Color(0xFF8E9A9B))
                )
            )
            .padding(0.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Myself",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {


        }

        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier.size(width = 380.dp, height = 450.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.quotes),
                            contentDescription = "quotesIcon",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(50.dp)
                                .clickable { openTasksScreen() }
                        )
                        Text(
                            text = "Add Notes",
                            fontSize = 16.sp
                        )
                    }



                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.chart),
                            contentDescription = "chartIcon",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(50.dp)
                                .clickable { openStatsScreen() }
                        )
                        Text(
                            text = "Statistic",
                            fontSize = 16.sp
                        )
                    }


                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_card_giftcard_24),
                            contentDescription = "WaterIntIcon",
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(end = 10.dp)
                                .size(50.dp)
                                .clickable { openQuotesScreen() }
                        )
                        Text(
                            text = "Daily Quotes",
                            fontSize = 16.sp
                        )
                    }


                }


            }
        }

        Spacer(Modifier.height(50.dp))

        // Logout Button
        Button(
            onClick = { onLogoutClick() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = "Logout")
        }
    }
}


@Composable
fun OptionButton(
    optionName: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = optionName,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MakeItSoTheme {
        MainScreenContent(
            openTasksScreen = { },
            openStatsScreen = { },
            openQuotesScreen = { },
            onLogoutClick = { }
        )
    }
}