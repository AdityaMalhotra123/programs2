package hu.ait.andwallet.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.andwallet.R
import hu.ait.andwallet.data.MoneyItem
import hu.ait.andwallet.data.MoneyType
import java.util.Date

@Composable
fun MainScreen(
    MoneyListViewModel: MoneyListViewModel = viewModel(),
    onNavigateToSummary: (Int, Int) -> Unit
) {
    var MoneyTitle by rememberSaveable {
        mutableStateOf("")
    }
    var MoneyAmount by rememberSaveable {
        mutableStateOf("")
    }
    var MoneyIncome by rememberSaveable {
        mutableStateOf(false)
    }
    var isError1 by remember { mutableStateOf(false) }
    var isError2 by remember { mutableStateOf(false) }

    Column {
        Row() {
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            OutlinedTextField(
                value = MoneyTitle,
                modifier = Modifier.weight(1f),
                isError = isError1,
                onValueChange = {
                    MoneyTitle = it
                    if (MoneyTitle.isEmpty()) {
                        isError1 = true
                    }
                    else {
                        isError1 = false
                    }
                                },
                label = { Text(text = stringResource(R.string.title)) },
                supportingText = {
                    if (isError1) {
                        Text(text = stringResource(R.string.error))
                    }
                },
                trailingIcon = {
                    if (isError1)
                        Icon(
                            Icons.Filled.Warning,
                            stringResource(R.string.error), tint = MaterialTheme.colorScheme.error)
                }
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            OutlinedTextField(
                value = MoneyAmount,
                modifier = Modifier.weight(1f),
                isError = isError2,
                onValueChange = {
                    MoneyAmount = it
                    if (MoneyAmount.isEmpty()) {
                        isError2 = true
                    }
                    else if (!MoneyAmount.isDigitsOnly()) {
                        isError2 = true
                    }
                    else {
                        isError2 = false
                    }},
                label = { Text(text = stringResource(R.string.amount_in))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                supportingText = {
                    if (isError2) {
                        Text(text = stringResource(R.string.error))
                    }
                },
                trailingIcon = {
                    if (isError2)
                        Icon(
                            Icons.Filled.Warning,
                            stringResource(R.string.error), tint = MaterialTheme.colorScheme.error)
                }
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        }


        Row() {
            Checkbox(checked = MoneyIncome, onCheckedChange = { MoneyIncome = it })
            Text(text = stringResource(R.string.income))
        }
        Row {
            Spacer(modifier = Modifier.width(8.dp))
            //Save Button
            Button(
                enabled = MoneyTitle.isNotEmpty() && MoneyAmount.isNotEmpty(),
                onClick = {
                MoneyListViewModel.addToMoneyList(
                    MoneyItem(
                        title = MoneyTitle,
                        amount = MoneyAmount.toInt(),
                        type = if (MoneyIncome) MoneyType.INCOME else MoneyType.EXPENSE,
                    )
                )
            }) {
                Text(text = stringResource(R.string.save))
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            //Delete All
            Button(onClick = {
                MoneyListViewModel.clearAllItems()
            }) {
                Text(text = stringResource(R.string.delete_all))
            }

            Spacer(modifier = Modifier.width(8.dp))
            //Summary Button
            Button(onClick = {
                onNavigateToSummary(
                    MoneyListViewModel.getIncome(),
                    MoneyListViewModel.getExpense()
                )
            }) {
                Text(text = stringResource(R.string.sum))
            }


        }

        // show MoneyItems from the ViewModel in a LazyColumn
        if (MoneyListViewModel.getAllItems().isEmpty()) {
            Text(text = stringResource(R.string.no_items))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(MoneyListViewModel.getAllItems()) {
                    TodoCard(it,
                        onRemoveItem = { MoneyListViewModel.removeItem(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoCard(
    moneyItem: MoneyItem,
    onRemoveItem: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = moneyItem.type.getIcon()),
                contentDescription = stringResource(R.string.priority),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )


            Text("${moneyItem.title} \n${moneyItem.amount} $", modifier = Modifier.fillMaxWidth(0.5f))


            Spacer(modifier = Modifier.fillMaxSize(0.55f))
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.delete),
                modifier = Modifier.clickable {
                    onRemoveItem()
                },
                tint = Color.Red
            )
        }
    }
}