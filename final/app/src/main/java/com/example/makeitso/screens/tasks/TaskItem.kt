
package com.example.makeitso.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.common.composable.DropdownContextMenu
import com.example.makeitso.common.ext.contextMenu
import com.example.makeitso.common.ext.hasDueDate
import com.example.makeitso.common.ext.hasDueTime
import com.example.makeitso.model.Task
import com.example.makeitso.theme.DarkOrange
import java.lang.StringBuilder

@Composable
@ExperimentalMaterialApi
fun TaskItem(
  task: Task,
  options: List<String>,
  onActionClick: (String) -> Unit
) {
  Card(
    backgroundColor = MaterialTheme.colors.background,
    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),

  ) {

    Row(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Spacer(modifier = Modifier.weight(1f))

      DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
    }
    Column(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {

      // Display due date at the top
      if (task.hasDueDate() || task.hasDueTime()) {
        Text(
          text = getDueDateAndTime(task),
          style = MaterialTheme.typography.body2,
          color = MaterialTheme.colors.secondary,
          modifier = Modifier.padding(bottom = 4.dp)
        )
      }

      // Display task title
      Text(
        text = task.title,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(bottom = 2.dp)
      )

      // Display task description
      Text(
        text = task.description,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
      )



    }
  }
}

private fun getDueDateAndTime(task: Task): String {
  val stringBuilder = StringBuilder("")

  if (task.hasDueDate()) {
    stringBuilder.append(task.dueDate)
    stringBuilder.append(" ")
  }

  if (task.hasDueTime()) {
    stringBuilder.append("at ")
    stringBuilder.append(task.dueTime)
  }

  return stringBuilder.toString()
}