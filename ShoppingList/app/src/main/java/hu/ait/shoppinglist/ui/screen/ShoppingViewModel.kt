package hu.ait.shoppinglist.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.shoppinglist.data.ShoppingDAO
import hu.ait.shoppinglist.data.ShoppingItem
import hu.ait.shoppinglist.data.ShoppingCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    val shoppingDAO: ShoppingDAO
) : ViewModel() {

    private var _shoppingList =
        mutableStateListOf<ShoppingItem>()

    fun getAllShoppingList(): Flow<List<ShoppingItem>> {
        return shoppingDAO.getAllItems()
    }

    suspend fun getAllShoppingNum(): Int {
        return shoppingDAO.getItemsNum()
    }
    

    fun addShoppingList(ShoppingItem: ShoppingItem) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingDAO.insert(ShoppingItem)
        }

    }


    fun removeItem(ShoppingItem: ShoppingItem) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingDAO.delete(ShoppingItem)
        }
    }

    fun editItem(editedItem: ShoppingItem) {
        viewModelScope.launch {
            shoppingDAO.update((editedItem))
        }
    }

    fun changeItemState(ShoppingItem: ShoppingItem, value: Boolean) {
        val updatedItem = ShoppingItem.copy()
        updatedItem.isDone = value
        viewModelScope.launch {
            shoppingDAO.update(updatedItem)
        }
    }

    fun clearAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingDAO.deleteAllItems()
        }
    }

}