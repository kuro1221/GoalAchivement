package com.example.goalachivement

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goalachivement.GoalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log

class GoalViewModel(private val repository: GoalRepository) : ViewModel() {
    var goals by mutableStateOf<List<String>>(listOf())

    fun loadGoals() {
        viewModelScope.launch(Dispatchers.IO) {
//            val loadedGoals = repository.getGoals()
//            Log.d("GoalTest", "出力； $loadedGoals");
            withContext(Dispatchers.Main) {
                goals = repository.getGoals()
            }
        }
    }

    fun addGoal(goal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGoal(goal)
//            val updatedGoals = repository.getGoals()
            withContext(Dispatchers.Main) {
//                goals = updatedGoals
                goals = repository.getGoals()
            }
        }
    }

    fun deleteGoal(goal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGoal(goal)
            val updatedGoals = repository.getGoals()
            withContext(Dispatchers.Main) {
                goals = updatedGoals
            }
        }
    }
}