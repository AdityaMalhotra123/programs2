package hu.ait.shoppinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDAO {
    @Query("SELECT * from shoppingtable")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Query("SELECT * from shoppingtable WHERE id = :id")
    fun getItem(id: Int): Flow<ShoppingItem>

    @Query("SELECT COUNT(*) from shoppingtable")
    suspend fun getItemsNum(): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: ShoppingItem)

    @Update
    suspend fun update(todo: ShoppingItem)

    @Delete
    suspend fun delete(todo: ShoppingItem)

    @Query("DELETE from shoppingtable")
    suspend fun deleteAllItems()
}