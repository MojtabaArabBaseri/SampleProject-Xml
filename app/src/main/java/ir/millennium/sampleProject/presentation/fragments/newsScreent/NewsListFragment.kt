package ir.millennium.sampleProject.presentation.fragments.newsScreent

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseFragment
import ir.millennium.sampleProject.core.utils.EndlessRecyclerOnScrollListener
import ir.millennium.sampleProject.core.utils.GridSpacingItemDecoration
import ir.millennium.sampleProject.data.dataSource.remote.UiState
import ir.millennium.sampleProject.data.model.remote.NewsItem
import ir.millennium.sampleProject.databinding.FragmentNewslistBinding
import ir.millennium.sampleProject.presentation.adapter.OnItemClickListener
import ir.millennium.sampleProject.presentation.adapter.RcvNewsListAdapter
import ir.millennium.sampleProject.presentation.utils.convertDpToPx
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class NewsListFragment : BaseFragment<FragmentNewslistBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var rcvNewsListAdapter: RcvNewsListAdapter

    internal val viewModel: NewsFragmentViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_newslist

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextView()
        initSwapRefreshing()
        initRcvNews()
        setupViewModel()
        getData()

    }

    private fun initTextView() = with(binding) {
        lblNoExistData.typeface = auxiliaryFunctionsManager.getTypefaceIranSansBoldPersian(context)
    }

    private fun initSwapRefreshing() {
        binding.refreshData.setOnRefreshListener(this@NewsListFragment)
        binding.refreshData.setProgressViewOffset(true, 0, convertDpToPx(70))
    }

    private fun initRcvNews() {

        val alphaInAnimationAdapter = AlphaInAnimationAdapter(rcvNewsListAdapter).apply {
            setDuration(1000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }

        val scrollListener = object : EndlessRecyclerOnScrollListener() {

            override fun onLoadMore(current_page: Int) {
                viewModel.getNextPage()
            }

            override fun onScrollTop(isVisible: Boolean) {}
        }

        binding.rcvNews.apply {
            addOnScrollListener(scrollListener)
            layoutManager = GridLayoutManager(requireContext(), 1)
            addItemDecoration(GridSpacingItemDecoration(1, convertDpToPx(4), true))
            adapter = alphaInAnimationAdapter
        }

        rcvNewsListAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onClick(newsItem: NewsItem) {
                navToDetailNewsFragment(newsItem)
            }
        })
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { dataResource ->
                renderUi(dataResource)
            }
        }
    }

    private fun getData() {
        viewModel.getNews(viewModel.params)
    }

    private fun renderUi(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                showLoadingMore()
            }

            is UiState.Success -> {
                hideLoading()
                rcvNewsListAdapter.submitList((uiState.data as List<NewsItem>))
            }

            is UiState.Error -> {
                hideLoading()
            }

            else -> {}
        }
    }

    private fun showLoadingMore() {
        if (viewModel.currentPage == 1) {
            binding.refreshData.isRefreshing = true
        } else {
            rcvNewsListAdapter.setLoadingState(true)
        }
    }

    private fun hideLoading() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            if (viewModel.currentPage == 1) {
                binding.refreshData.isRefreshing = false
            } else {
                rcvNewsListAdapter.setLoadingState(false)
            }
        }
    }

    override fun onScrollToTop() {
        binding.apply {
            rcvNews.smoothScrollToPosition(0)
        }
    }

    internal fun navToDetailNewsFragment(newsItem: NewsItem) {
        mainHelper.bottomNavigationVisibility(true)
        mainHelper.navigate(
            NewsListFragmentDirections.actionNewsListFragmentToDetailNewsFragmentFragment(newsItem)
        )
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.refresh()
    }
}