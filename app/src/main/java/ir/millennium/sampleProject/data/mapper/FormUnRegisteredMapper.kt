package ir.millennium.sampleProject.data.mapper

import ir.millennium.sampleProject.data.model.local.formUnregistered.FormUnRegisteredEntity
import ir.millennium.sampleProject.data.model.local.formUnregistered.FormUnRegisteredModel

fun FormUnRegisteredEntity.mapToUiModel() = FormUnRegisteredModel(this.apiLevel, this.name)

fun List<FormUnRegisteredEntity>.mapToUiModelList() = map {
    it.mapToUiModel()
}

fun FormUnRegisteredModel.mapToEntity() = FormUnRegisteredEntity(this.id, this.name)

fun List<FormUnRegisteredModel>.mapToEntityList() = map {
    it.mapToEntity()
}