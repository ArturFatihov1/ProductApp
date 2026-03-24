package com.example.productapp.load

import com.example.productapp.detail.FakeRunAsync
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoadViewModel
    private lateinit var repository: FakeLoadRepository
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        repository = FakeLoadRepository()
        runAsync = FakeRunAsync()
        viewModel = LoadViewModel(repository, runAsync)
    }

    @Test
    fun sameFragment() = runTest {
        viewModel.load(isFirstRun = true)

        assertEquals(LoadUiState.Progress, viewModel.state.getOrAwaitValue())

        assertEquals(1, repository.loadCalledCount)

        runAsync.returnResult()

        assertEquals(LoadUiState.Success, viewModel.state.getOrAwaitValue())
    }

    @Test
    fun recreateActivity() = runTest {
        repository.expectFailure()

        viewModel.load(isFirstRun = true)

        assertEquals(LoadUiState.Progress, viewModel.state.getOrAwaitValue())
        assertEquals(1, repository.loadCalledCount)

        runAsync.returnResult()

        assertEquals(LoadUiState.ErrorRes, viewModel.state.getOrAwaitValue())

        // имитация пересоздания Activity / Fragment
        viewModel.load(isFirstRun = false)

        assertEquals(1, repository.loadCalledCount)
        assertEquals(LoadUiState.ErrorRes, viewModel.state.getOrAwaitValue())
    }
}

private class FakeLoadRepository : LoadRepository {

    var loadCalledCount = 0
    private var success = true

    fun expectFailure() {
        success = false
    }

    override suspend fun load() {
        loadCalledCount++
        if (!success) throw NoInternetConnectionException()
    }
}