package ir.millennium.sampleProject.data.dataSource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.millennium.sampleProject.data.model.local.formUnregistered.NewsEntity

@Dao
interface RoomServiceDao {

    @Query("SELECT * FROM news")
    suspend fun getAndroidVersions(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsEntity: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun clear()
}