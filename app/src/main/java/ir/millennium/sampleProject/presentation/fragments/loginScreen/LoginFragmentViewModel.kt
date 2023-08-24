package ir.millennium.sampleProject.presentation.fragments.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleProject.data.dataSource.remote.UiState
import ir.millennium.sampleProject.domain.useCase.GetNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    val dataResource = MutableStateFlow<UiState>(UiState.Initialization)

    fun checkAuthentication() {
        val params = mutableMapOf<String, Any>()
        getNewsUseCase.getNews(params)
            .flowOn(Dispatchers.IO)
            .map { movieList ->
                dataResource.value = UiState.Success(movieList)
            }
            .onStart {
                dataResource.value = UiState.Loading
            }.retry(retries = 3) { throwable ->
                val shouldRetry = throwable is IOException
                if (shouldRetry) {
                    delay(1000)
                }
                shouldRetry
            }
            .catch { throwable ->
                dataResource.value = UiState.Error(throwable)
            }.launchIn(viewModelScope)
    }
}