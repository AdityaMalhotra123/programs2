package hu.ait.shoppinglist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import hu.ait.shoppinglist.data.ShoppingItem
import hu.ait.shoppinglist.data.ShoppingCategory
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    shoppingViewModel: ShoppingViewModel = hiltViewModel(),
    onNavigateToItem: (String, String, Float, Boolean) -> Unit
) {

    var showAddDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Shopping List")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        showAddDialog = true
                    }) {
                        Icon(Icons.Filled.Add, null)
                    }

                    IconButton(onClick = {
                        shoppingViewModel.clearAllItems()
                    }) {
                        Icon(Icons.Filled.Delete, null)
                    }
                    /*IconButton(onClick = {
                        coroutineScope.launch {
                            onNavigateToSummary(
                                ViewModel.getAllTodoNum(),
                                shoppingViewModel.getImportantTodoNum()
                            )
                        }

                    }) {
                        Icon(Icons.Filled.Info, null)
                    }**/
                }
            )
        },
        content = {
            ShoppingListContent(
                Modifier.padding(it), shoppingViewModel, onNavigateToItem
            )

            if (showAddDialog) {
                AddNewItemDialog(shoppingViewModel,
                    onDismissRequest = {
                        showAddDialog = false
                    })
            }
        }
    )
}
@Composable
fun ShoppingListContent(
    modifier: Modifier,
    shoppingViewModel: ShoppingViewModel,
    onNavigateToItem: (String, String, Float, Boolean) -> Unit
) {
    val shoppingList by shoppingViewModel.getAllShoppingList().collectAsState(emptyList())

    var itemToEdit: ShoppingItem? by rememberSaveable {
        mutableStateOf(null)
    }

    var showEditTodoDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        if (shoppingList.isEmpty()) {
            Text(text = "No items")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(shoppingList) {
                    ShoppingCard(it,
                        onItemCheckChange = { checkValue ->
                            shoppingViewModel.changeItemState(it, checkValue)
                        },
                        onRemoveItem = { shoppingViewModel.removeItem(it) },
                        onEditItem = {ShoppingItem ->
                            itemToEdit = it
                            showEditTodoDialog= true
                        },
                        onNavigateToItem = onNavigateToItem
                    )
                }
            }

            if (showEditTodoDialog) {
                AddNewItemDialog(shoppingViewModel = shoppingViewModel, itemToEdit = itemToEdit) {
                    showEditTodoDialog = false

                }
            }
        }

    }
}

@Composable
fun ShoppingCard(
    ShoppingItem: ShoppingItem,
    onItemCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
    onEditItem: (ShoppingItem) -> Unit = {},
    onNavigateToItem: (String, String, Float, Boolean) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        colors = if (!ShoppingItem.status) CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ) else CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.outlineVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = ShoppingItem.category.getIcon()),
                    contentDescription = "Category",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )


                Text(ShoppingItem.title, modifier = Modifier.fillMaxWidth(0.2f))
                Spacer(modifier = Modifier.fillMaxSize(0.35f))
                Checkbox(checked = ShoppingItem.status, onCheckedChange = {})
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Details",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            onNavigateToItem(ShoppingItem.title, ShoppingItem.description, ShoppingItem.price, ShoppingItem.status)
                        }

                    },
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onEditItem(ShoppingItem)
                    },
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onRemoveItem()
                    },
                    tint = Color.Red
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else
                            Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        }
                    )
                }
            }

            if (expanded) {
                Text(text = "Description: ${ShoppingItem.description}")
                Text(text = "Price: ${ShoppingItem.price}")
                Text(text = "Status (Bought?): ${ShoppingItem.status}")
            }
        }
    }
}

@Composable
fun AddNewItemDialog(
    shoppingViewModel: ShoppingViewModel,
    itemToEdit: ShoppingItem? = null,
    onDismissRequest: () -> Unit
) {
    var itemTitle by rememberSaveable {
        mutableStateOf(itemToEdit?.title ?: "")
    }
    var itemDescription by rememberSaveable {
        mutableStateOf(itemToEdit?.description ?: "")
    }
    var itemCategory by rememberSaveable {
        mutableStateOf("Food")
    }
    var itemPrice by rememberSaveable {
        mutableStateOf("")
    }
    var itemStatus by rememberSaveable {
        mutableStateOf(false)
    }
    var isValid by rememberSaveable { mutableStateOf(true) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text(
                    text = if (itemToEdit == null) "Add Item" else "Edit Item",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )

                OutlinedTextField(
                    value = itemTitle,
                    onValueChange = {
                        itemTitle = it
                        isValid = it.isNotEmpty()
                    },
                    label = { Text(text = "Enter item here:") },
                    isError = !isValid
                )
                OutlinedTextField(
                    value = itemDescription,
                    onValueChange = {
                        itemDescription = it
                        isValid = it.isNotEmpty()
                    },
                    label = { Text(text = "Enter item description:") },
                    isError = !isValid
                )
                OutlinedTextField(
                    value = itemPrice,
                    onValueChange = {
                        itemPrice = it
                        isValid = it.isNotEmpty() && it.isDigitsOnly()
                    },
                    label = { Text(text = "Enter item Price:") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = !isValid
                )

                if (!isValid) {
                    Text(text = "Please enter valid text", color = Color.Red)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = itemStatus, onCheckedChange = { itemStatus = it })
                    Text(text = "Bought?")
                }
                itemCategory = displaySpinner()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        enabled = isValid && (itemTitle != "") && (itemDescription != ""),
                        onClick = {
                            if (itemToEdit == null) {
                                shoppingViewModel.addShoppingList(
                                    ShoppingItem(
                                        title = itemTitle,
                                        description = itemDescription,
                                        price = itemPrice.toFloat(),
                                        status = itemStatus,
                                        category = when (itemCategory) {
                                            "Food" -> ShoppingCategory.FOOD
                                            "Electronics" -> ShoppingCategory.ELECTRONICS
                                            else -> ShoppingCategory.OTHERS
                                        },
                                        isDone = false
                                    )
                                )
                            } else {
                                val editedItem = itemToEdit.copy(
                                    title = itemTitle,
                                    description = itemDescription,
                                    price = itemPrice.toFloat(),
                                    status = itemStatus,
                                    category = when (itemCategory) {
                                        "Food" -> ShoppingCategory.FOOD
                                        "Electronics" -> ShoppingCategory.ELECTRONICS
                                        else -> ShoppingCategory.OTHERS
                                    },
                                )
                                shoppingViewModel.editItem(editedItem)
                            }

                            onDismissRequest()
                        }
                    ) {
                        Text(text = "Save")
                    }
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel")
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun displaySpinner(): String {
    val options = listOf("Food", "Electronics", "Others")
    var expandedState by rememberSaveable { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf(options[0]) }
    
    ExposedDropdownMenuBox(expanded = expandedState, onExpandedChange = {expandedState = !expandedState}) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState)},
            readOnly = true,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            modifier = Modifier.menuAnchor()
            )
        
        ExposedDropdownMenu(expanded = expandedState, onDismissRequest = { expandedState = false}) {
            options.forEach{
                item -> DropdownMenuItem(
                text = { Text(text = item) },
                onClick = {
                    selectedOption = item
                    expandedState = false
                }
            )
            }
        }
    }
    return selectedOption
}
