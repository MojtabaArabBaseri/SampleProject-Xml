package ir.millennium.sampleProject.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dagger.hilt.android.qualifiers.ActivityContext
import ir.millennium.sampleProject.core.utils.AuxiliaryFunctionsManager
import ir.millennium.sampleProject.data.model.remote.NewsItem
import ir.millennium.sampleProject.databinding.ItemLoadingMoreStateBinding
import ir.millennium.sampleProject.databinding.ItemNewsListBinding
import javax.inject.Inject

class RcvNewsListAdapter @Inject constructor(
    @ActivityContext val context: Context,
    val glide: RequestManager,
    private val auxiliaryFunctionsManager: AuxiliaryFunctionsManager
) : ListAdapter<NewsItem, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.title == newItem.publishedAt
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }
    }
) {
    lateinit var onItemClickListener: OnItemClickListener
    var isNetworkStateLoading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_NORMAL -> {
                val view = ItemNewsListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ArticleListViewHolder(view)
            }

            VIEW_LOADING -> {
                val view = ItemLoadingMoreStateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ArticleListLoadingViewHolder(view)
            }

            else -> throw IllegalStateException("Ops")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleListViewHolder)
            holder.onBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (isNetworkStateLoading && position == itemCount - 1) VIEW_LOADING else VIEW_NORMAL
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isNetworkStateLoading) 1 else 0
    }

    fun setLoadingState(isNetworkStateLoading: Boolean) {
        val previousState = this.isNetworkStateLoading
        this.isNetworkStateLoading = isNetworkStateLoading
        if (previousState != isNetworkStateLoading) {
            if (isNetworkStateLoading)
                notifyItemInserted(super.getItemCount())
            else
                notifyItemRemoved(super.getItemCount())
        }
    }

    internal fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ArticleListViewHolder(private val binding: ItemNewsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.lblTitle.typeface =
                auxiliaryFunctionsManager.getTypefaceIranSansEnglish(context)
            binding.lblAuther.typeface =
                auxiliaryFunctionsManager.getTypefaceIranSansEnglish(context)
            binding.lblPublishAt.typeface =
                auxiliaryFunctionsManager.getTypefaceIranSansEnglish(context)
        }

        fun onBind(item: NewsItem) {
            binding.lblTitle.text = item.title
            binding.lblAuther.text = item.author
            binding.lblPublishAt.text = item.publishedAt

            glide.load(item.urlToImage).into(binding.imvImageNews)
            binding.root.setOnClickListener {
                onItemClickListener.onClick(item)

            }
        }
    }

    class ArticleListLoadingViewHolder(binding: ItemLoadingMoreStateBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val VIEW_NORMAL = 0
        const val VIEW_LOADING = 1
    }
}

