package ir.millennium.sampleProject.data.dataSource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.millennium.sampleProject.data.model.local.formUnregistered.FormUnRegisteredEntity

@Database(entities = [FormUnRegisteredEntity::class], version = 1, exportSchema = false)
abstract class SampleProjectDatabase : RoomDatabase() {

    abstract fun roomServiceDao(): RoomServiceDao

}

