package com.example.goalachivement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import com.example.goalachivement.ui.theme.GoalAchivementTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.material.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoalScreen() {
    val viewModel = GoalViewModel(GoalRepository())

    // 画面が最初に表示されるときに目標をロード
    LaunchedEffect(Unit) {
        viewModel.loadGoals()
    }

    var goalText by remember { mutableStateOf("") }
    var goalsList by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 2. 目標入力エリア
        TextField(
            value = goalText,
            onValueChange = { goalText = it },
            label = { Text("新しい目標") },
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.addGoal(goalText)
                goalText = ""
            }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            shape = RoundedCornerShape(4.dp),
            colors = TextFieldDefaults.textFieldColors()
        )

        // 3. 目標一覧エリア
        Text(text = "現在の目標")
//        Text(text = "現在の目標", style = MaterialTheme.typography.h6)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.goals) { goal ->
                GoalItem(goal, onDelete = {
                    viewModel.deleteGoal(goal)
                })
            }
        }
    }
}

@Composable
fun GoalItem(goal: String, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray.copy(alpha = 0.2f))
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = goal)
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Goal")
        }
    }
}