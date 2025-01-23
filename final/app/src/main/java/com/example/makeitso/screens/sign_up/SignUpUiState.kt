
package com.example.makeitso.screens.sign_up

data class SignUpUiState(
  val firstName: String = "",
  val lastName: String = "",
  val email: String = "",
  val password: String = "",
  val repeatPassword: String = ""
)