package com.google.firebase.example.makeitso.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.ui.shared.LoadingIndicator
import com.google.firebase.example.makeitso.ui.shared.SIwGButton
import com.google.firebase.example.makeitso.ui.theme.DarkBlue
import kotlinx.serialization.Serializable

@Serializable
object SignInRoute

@Composable
fun SignInScreen(
    openSignUpScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value is SignInUiState.Loading) {
        LoadingIndicator()
    } else {
        SignInScreenContent(
            openSignUpScreen = openSignUpScreen,
            viewModel = viewModel
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignInScreenContent(
    openSignUpScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            val (appLogo, form, signUpText) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(appLogo) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(24.dp))

                Image(
                    modifier = Modifier.size(88.dp),
                    painter = painterResource(id = R.mipmap.ic_launcher_round),
                    contentDescription = "App logo"
                )

                Spacer(Modifier.size(24.dp))
            }

            Column(
                modifier = Modifier
                    .constrainAs(form) {
                        top.linkTo(appLogo.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(24.dp))

                SIwGButton()
            }

            Column(
                modifier = Modifier
                    .constrainAs(signUpText) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = openSignUpScreen) {
                    Text(
                        text = stringResource(R.string.sign_up_text),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = DarkBlue
                    )
                }

                Spacer(Modifier.size(24.dp))
            }
        }
    }
}