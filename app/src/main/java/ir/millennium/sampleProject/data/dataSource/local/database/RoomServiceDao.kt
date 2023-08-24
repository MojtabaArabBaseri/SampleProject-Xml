package ir.millennium.sampleProject.data.dataSource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.millennium.sampleProject.data.model.local.formUnregistered.FormUnRegisteredEntity

@Dao
interface RoomServiceDao {

    @Query("SELECT * FROM formUnRegistered")
    suspend fun getAndroidVersions(): List<FormUnRegisteredEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formUnRegisteredEntity: FormUnRegisteredEntity)

    @Query("DELETE FROM formUnRegistered")
    suspend fun clear()
}