package com.example.makeitso.common.composable

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.makeitso.R

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