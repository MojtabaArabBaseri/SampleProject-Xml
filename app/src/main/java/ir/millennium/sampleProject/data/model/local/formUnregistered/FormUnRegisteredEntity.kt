package ir.millennium.sampleProject.data.model.local.formUnregistered

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formUnRegistered")
data class FormUnRegisteredEntity(@PrimaryKey val apiLevel: Int, val name: String)