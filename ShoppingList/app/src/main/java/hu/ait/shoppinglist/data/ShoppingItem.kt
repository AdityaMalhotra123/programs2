package hu.ait.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.ait.shoppinglist.R

@Entity(tableName = "shoppingtable")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "status") val status:Boolean,
    @ColumnInfo(name = "category") var category:ShoppingCategory,
    @ColumnInfo(name = "isDone") var isDone: Boolean
)

enum class ShoppingCategory {
    FOOD, ELECTRONICS, OTHERS;

    fun getIcon(): Int {
        return if (this == FOOD) R.drawable.food else if (this == ELECTRONICS) R.drawable.electronics else R.drawable.stuff
    }
}