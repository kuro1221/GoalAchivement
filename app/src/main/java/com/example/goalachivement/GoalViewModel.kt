package com.example.goalachivement

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.goalachivement.GoalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalViewModel(private val repository: GoalRepository) : ViewModel() {
    var goals by mutableStateOf<List<String>>(listOf())

    fun loadGoals() {
        CoroutineScope(Dispatchers.IO).launch {
            goals = repository.getGoals()
        }
    }

    fun addGoal(goal: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addGoal(goal)
            loadGoals()
        }
    }

    fun deleteGoal(goal: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteGoal(goal)
            loadGoals()
        }
    }
}
