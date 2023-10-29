package com.example.goalachivement

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import android.util.Log
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After


class FakeGoalRepository : GoalRepository() {
    // テスト用の初期データをオーバーライド
    override val dummyGoals = mutableListOf(
        "初期目標1",
        "初期目標2",
        "初期目標3"
    )
}

class GoalViewModelTest {

    // テスト対象のViewModel
    private lateinit var viewModel: GoalViewModel

    // Fakeのリポジトリ
    private val fakeRepository = FakeGoalRepository()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = GoalViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadGoals() = runBlockingTest {
        viewModel.loadGoals()
//        Log.d("GoalTest", "出力；test");
        // 初期の目標リストとViewModelの目標が一致することを確認
        assertEquals(listOf("初期目標1", "初期目標2", "初期目標3"), viewModel.goals)
    }

    @Test
    fun testAddGoal() = runBlockingTest {
        // 新しい目標を追加
        val newGoal = "新しい目標"
        viewModel.addGoal(newGoal)
        advanceUntilIdle()
        // 新しい目標がリストに追加されていることを確認
        assertEquals(listOf("初期目標1", "初期目標2", "初期目標3", "新しい目標"), viewModel.goals)
//        assertTrue(viewModel.goals.contains(newGoal))
    }

    @Test
    fun testDeleteGoal() = runBlockingTest {
        val deleteGoal = "初期目標1"
        viewModel.deleteGoal(deleteGoal)
        advanceUntilIdle()
        assertFalse(viewModel.goals.contains(deleteGoal))
    }
}