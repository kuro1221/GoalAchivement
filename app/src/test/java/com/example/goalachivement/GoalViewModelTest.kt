package com.example.goalachivement

import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GoalViewModelTest {

    private lateinit var goalRepository: GoalRepository
    private lateinit var viewModel: GoalViewModel

    @Before
    fun setUp() {
        goalRepository = mock()
        viewModel = GoalViewModel(goalRepository)
    }

    @Test
    fun testLoadGoals() = runBlockingTest {
        // Given
        val expectedGoals = listOf("スパルタンレース完走", "簿記2級", "応用情報処理試験合格")
        doReturn(expectedGoals).`when`(goalRepository).getGoals()

        // When
        viewModel.loadGoals()

        // Then
        assertEquals(expectedGoals, viewModel.goals)
    }

    @Test
    fun testAddGoal() = runBlockingTest {
        // Given
        val newGoal = "新しい目標"

        // When
        viewModel.addGoal(newGoal)

        // Then
        assert(viewModel.goals.contains(newGoal))
        verify(goalRepository).addGoal(newGoal)
    }

    @Test
    fun testDeleteGoal() = runBlockingTest {
        // Given
        val deleteGoal = "簿記2級"
        viewModel.goals = mutableListOf("スパルタンレース完走", deleteGoal, "応用情報処理試験合格")

        // When
        viewModel.deleteGoal(deleteGoal)

        // Then
        assert(!viewModel.goals.contains(deleteGoal))
        verify(goalRepository).deleteGoal(deleteGoal)
    }
}
