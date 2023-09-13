package ir.millennium.sampleProject.presentation.fragments.newsScreent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.millennium.sampleProject.data.dataSource.remote.UiState
import ir.millennium.sampleProject.data.model.remote.ResponseNewsModel
import ir.millennium.sampleProject.domain.useCase.GetNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NewsFragmentViewModelTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: NewsFragmentViewModel

    @Inject
    lateinit var getNewsUseCase: GetNewsUseCase

    @Before
    fun testPreconditions() {
        hiltRule.inject()
        viewModel = NewsFragmentViewModel(getNewsUseCase)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    @DisplayName("check first value current page")
    fun checkFirstValueCurrentPage() {
        val actual = viewModel.currentPage
        assertEquals(1, actual)
    }

    @Test(expected = Exception::class)
    @DisplayName("check status UiState")
    fun checkStatusUiState() {

        val states = mutableListOf<UiState>()
        viewModel.viewModelScope.launch {
            viewModel.dataResource.collect {
                states.add(it)
            }
        }

        viewModel.getNews(viewModel.params)
        dispatcher.scheduler.advanceUntilIdle()
        assert(states[0] is UiState.Initialization)
        assert(states[1] is UiState.Loading)
    }

    @Test
    @DisplayName("check status UiState")
    fun checkStatusSuccessUiState() = runTest {

        val responseNewsModel = flow {
            emit(ResponseNewsModel(articles = emptyList()))
        }

        `when`(getNewsUseCase.getNews(anyOrNull())).thenReturn(responseNewsModel)

        val actual = getNewsUseCase.getNews(anyOrNull())
        dispatcher.scheduler.advanceUntilIdle()

        actual.collect {
            assertEquals(0, it.articles?.size)
        }
    }
}