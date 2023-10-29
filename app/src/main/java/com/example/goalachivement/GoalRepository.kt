package com.example.goalachivement

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class GoalRepository {
    // ダミーの目標リスト
    private val dummyGoals = mutableListOf(
        "スパルタンレース完走",
        "簿記2級",
        "応用情報処理試験合格"
    )

    // 排他制御のためのMutex
    private val mutex = Mutex()

    // 目標のリストを取得
    suspend fun getGoals(): List<String> {
        return mutex.withLock {
            dummyGoals.toList()
        }
    }

    // 目標を追加
    suspend fun addGoal(goal: String) {
        mutex.withLock {
            dummyGoals.add(goal)
        }
    }

    // 目標を削除
    suspend fun deleteGoal(goal: String) {
        mutex.withLock {
            dummyGoals.remove(goal)
        }
    }
}
