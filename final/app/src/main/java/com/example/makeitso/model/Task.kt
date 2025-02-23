
package com.example.makeitso.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Task(
  @DocumentId val id: String = "",
  @ServerTimestamp val createdAt: Date = Date(),
  val title: String = "",
  val priority: String = "",
  val dueDate: String = "",
  val dueTime: String = "",
  val description: String = "",
  val completed: Boolean = false,
  val userId: String = ""
)
