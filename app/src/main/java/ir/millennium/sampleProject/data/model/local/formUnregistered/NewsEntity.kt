package ir.millennium.sampleProject.data.model.local.formUnregistered

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(@PrimaryKey val apiLevel: Int, val name: String)