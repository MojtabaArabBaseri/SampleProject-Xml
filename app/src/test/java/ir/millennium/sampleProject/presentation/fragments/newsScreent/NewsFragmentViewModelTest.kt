package ir.millennium.sampleProject.presentation.fragments.newsScreent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import ir.millennium.sampleProject.data.dataSource.remote.ApiService
import ir.millennium.sampleProject.data.dataSource.remote.UiState
import ir.millennium.sampleProject.data.repository.remote.RemoteRepositoryImpl
import ir.millennium.sampleProject.domain.useCase.GetNewsUseCase
import ir.millennium.sampleProject.presentation.utils.Constants.BASIC_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import retrofit2.Retrofit
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NewsFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: NewsFragmentViewModel

    @Inject
    lateinit var apiService: ApiService

    @BeforeEach
    fun setUp() {
        viewModel = NewsFragmentViewModel(
            GetNewsUseCase(
                RemoteRepositoryImpl(
                    Retrofit.Builder().baseUrl(BASIC_URL).build().create(ApiService::class.java)
                )
            )
        )
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `check first value current page`() {
        val actual = viewModel.currentPage
        assertEquals(1, actual)
    }

    @Test
    fun `check first value with mock`() {
        Mockito.`when`(viewModel.currentPage).thenReturn(6)
        val actual = viewModel.currentPage
        assertEquals(6, actual)
    }

    @Test
    fun `check status UiState`() {

        val states = mutableListOf<UiState>()
        viewModel.viewModelScope.launch {
            viewModel.dataResource.collect {
                states.add(it)
            }
        }

        viewModel.getNews()
        dispatcher.scheduler.advanceUntilIdle()
        assert(states[0] is UiState.Initialization)
        assert(states[1] is UiState.Loading)
    }

}