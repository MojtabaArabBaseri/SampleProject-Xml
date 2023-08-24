package ir.millennium.sampleProject.presentation.adapter

import ir.millennium.sampleProject.data.model.remote.NewsItem

interface OnItemClickListener {
    fun onClick(newsItem: NewsItem)
}