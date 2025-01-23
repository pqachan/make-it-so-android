
package com.example.makeitso.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.makeitso.MAIN_SCREEN
import com.example.makeitso.R.string as AppText
import com.example.makeitso.SETTINGS_SCREEN
import com.example.makeitso.SIGN_UP_SCREEN
import com.example.makeitso.common.ext.isValidEmail
import com.example.makeitso.common.ext.isValidPassword
import com.example.makeitso.common.ext.passwordMatches
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.LogService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val accountService: AccountService,
  logService: LogService
) : MakeItSoViewModel(logService) {
  var uiState = mutableStateOf(SignUpUiState())
    private set

  private val firstName
    get() = uiState.value.firstName
  private val lastName
    get() = uiState.value.lastName
  private val email
    get() = uiState.value.email
  private val password
    get() = uiState.value.password

  fun onFirstNameChange(newValue: String) {
    uiState.value = uiState.value.copy(firstName = newValue)
  }

  fun onLastNameChange(newValue: String) {
    uiState.value = uiState.value.copy(lastName = newValue)
  }

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun resetFields() {
    uiState.value = SignUpUiState(firstName = "", lastName = "", email = "", password = "", repeatPassword = "")
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }

    launchCatching {
      accountService.linkAccount(email, password)
      openAndPopUp(MAIN_SCREEN, SIGN_UP_SCREEN)
    }
  }
}