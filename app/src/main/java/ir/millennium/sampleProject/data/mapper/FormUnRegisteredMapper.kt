package ir.millennium.sampleProject.data.mapper

import ir.millennium.sampleProject.data.model.local.formUnregistered.NewsEntity
import ir.millennium.sampleProject.data.model.local.formUnregistered.NewsModel

fun NewsEntity.mapToUiModel() = NewsModel(this.apiLevel, this.name)

fun List<NewsEntity>.mapToUiModelList() = map {
    it.mapToUiModel()
}

fun NewsModel.mapToEntity() = NewsEntity(this.id, this.name)

fun List<NewsModel>.mapToEntityList() = map {
    it.mapToEntity()
}