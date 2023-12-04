package ir.millennium.sampleProject.presentation.fragments.newsScreent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleProject.data.dataSource.remote.UiState
import ir.millennium.sampleProject.data.model.remote.NewsItem
import ir.millennium.sampleProject.domain.useCase.GetNewsUseCase
import ir.millennium.sampleProject.presentation.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
open class NewsFragmentViewModel @Inject constructor(
    val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private var allNews = emptyList<NewsItem>().toMutableList()

    val dataResource = MutableStateFlow<UiState>(UiState.Initialization)

    private var _currentPage = 1
    val currentPage
        get() = _currentPage

    val params = mutableMapOf<String, Any>()

    init {
        params["apiKey"] = API_KEY
        params["from"] = "2023-07-00"
        params["q"] = "tesla"
        params["page"] = _currentPage
    }

    fun getNews(params: MutableMap<String, Any>) {

        params.replace("page", _currentPage)

        getNewsUseCase.getNews(params)
            .flowOn(Dispatchers.IO)
            .map { newsList ->
                newsList.articles?.let { allNews.addAll(it) }
                dataResource.value = UiState.Success(allNews)
            }
            .onStart {
                dataResource.value = UiState.Loading
            }
            .catch { throwable ->
                dataResource.value = UiState.Error(throwable)
            }.launchIn(viewModelScope)

    }

    fun refresh() {
        _currentPage = 1
        allNews.clear()
        getNews(params)
    }

    fun getNextPage() {
        _currentPage++
        getNews(params)
    }
}