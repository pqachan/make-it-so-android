package com.example.makeitso.screens.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.R
import com.example.makeitso.R.string as AppText
import com.example.makeitso.theme.MakeItSoTheme

@Composable
fun LoginScreen(
  openAndPopUp: (String, String) -> Unit,
  openScreen: (String) -> Unit,
  viewModel: LoginViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState

  LoginScreenContent(
    uiState = uiState,
    onEmailChange = viewModel::onEmailChange,
    onPasswordChange = viewModel::onPasswordChange,
    onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
    onSignUpClick = { viewModel.onSignUpClick(openScreen) },
    onForgotPasswordClick = viewModel::onForgotPasswordClick
  )
}

@Composable
fun LoginScreenContent(
  modifier: Modifier = Modifier,
  uiState: LoginUiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onSignInClick: () -> Unit,
  onSignUpClick: () -> Unit,
  onForgotPasswordClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFB2FF59)) // Background color
      .padding(32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Image(
      painter = painterResource(id = R.drawable.smiley),
      contentDescription = "MySelf Logo",
      modifier = Modifier.size(120.dp)
    )
    Text("MySelf", fontWeight = FontWeight.Bold, fontSize = 32.sp)

    Spacer(modifier = Modifier.height(32.dp))

    OutlinedTextField(
      value = uiState.email,
      onValueChange = onEmailChange,
      label = { Text("Username / Email") },
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      value = uiState.password,
      onValueChange = onPasswordChange,
      label = { Text("Password") },
      visualTransformation = PasswordVisualTransformation(),
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
      onClick = onSignInClick,
      modifier = Modifier.fillMaxWidth(),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
      shape = RoundedCornerShape(8.dp)
    ) {
      Text("Login", color = Color.White)
    }

    Spacer(modifier = Modifier.height(16.dp))

    TextButton(onClick = onForgotPasswordClick) {
      Text("Forgot Password", color = Color(0xFF6200EA))
    }

    Spacer(modifier = Modifier.height(8.dp))
    TextButton(onClick = onSignUpClick) {
      Text("Don't have an account? Sign up", color = Color(0xFF6200EA))
    }

  }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
  val uiState = LoginUiState(
    email = "email@test.com"
  )

  MakeItSoTheme {
    LoginScreenContent(
      uiState = uiState,
      onEmailChange = { },
      onPasswordChange = { },
      onSignInClick = { },
      onSignUpClick = { },
      onForgotPasswordClick = { }
    )
  }
}