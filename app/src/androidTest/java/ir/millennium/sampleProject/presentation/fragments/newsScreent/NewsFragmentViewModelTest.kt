package ir.millennium.sampleProject.presentation.fragments.newsScreent

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.millennium.sampleProject.MainCoroutineRule
import ir.millennium.sampleProject.core.utils.AuxiliaryFunctionsManager
import ir.millennium.sampleProject.data.dataSource.remote.UiState
import ir.millennium.sampleProject.domain.useCase.GetNewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
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
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: NewsFragmentViewModel

    @Inject
    lateinit var getNewsUseCase: GetNewsUseCase

    @Before
    fun testPreconditions() {
        hiltRule.inject()
        viewModel = NewsFragmentViewModel(getNewsUseCase)
    }

    @Test
    @DisplayName("check first value current page")
    fun checkFirstValueCurrentPage() {
        val actual = viewModel.currentPage
        assertEquals(1, actual)
    }

    @Test
    @DisplayName("check status UiState")
//    fun checkStatusUiState() = runTest(dispatchTimeoutMs = 10000) {
    fun uistateSuccess_whenReciveResponseFromServer() = runTest {

        val testResults = mutableListOf<UiState>()
        val job = launch {
            viewModel.dataResource.collect {
                testResults.add(it)
                if ((it is UiState.Success) or (it is UiState.Error)) {
                    cancel()
                }
            }
        }

        viewModel.getNews(viewModel.params)
//        advanceUntilIdle()
        job.join()
        assert(testResults[0] is UiState.Loading)
        assert(testResults[1] is UiState.Success)
        job.cancel()
    }

    @Test
    @DisplayName("check status UiState")
    fun uistateError_whenDontReciveResponseFromServer() = runTest {

        val testResults = mutableListOf<UiState>()
        val job = launch {
            viewModel.dataResource.collect {
                testResults.add(it)
                if ((it is UiState.Success) or (it is UiState.Error)) {
                    cancel()
                }
            }
        }

        val auxiliaryFunctionsManager = Mockito.mock(AuxiliaryFunctionsManager::class.java)

        Mockito.`when`(auxiliaryFunctionsManager.isNetworkConnected(anyOrNull())).thenReturn(false)

        if (!auxiliaryFunctionsManager.isNetworkConnected(anyOrNull())) {
            val getNewsUseCase = Mockito.mock(viewModel.getNewsUseCase::class.java)
            BDDMockito.given(getNewsUseCase.getNews(anyOrNull()))
                .willAnswer { UiState.Error(Throwable()) }
            viewModel.getNews(viewModel.params)
            job.join()

            assert(testResults[0] is UiState.Loading)
            assert(testResults[1] is UiState.Error)
        }
        job.cancel()
//        val viewModel = Mockito.mock(NewsFragmentViewModel::class.java)
//        BDDMockito.given(viewModel.getNewsUseCase.getNews(anyOrNull())).willAnswer { throw IOException() }
//        viewModel.getNews(anyOrNull())

    }

}