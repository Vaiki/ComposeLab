package com.vaiki.composelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaiki.composelab.ui.theme.ComposeLabTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipTimeScreen()
                }
            }
        }
    }

    // здесь отображено "подъем состояния" это когда сохраненое состояние (в данном случае amountInput) надо применить в разных функциях
    // (в данном случае для набора суммы чека(EditNumberField(value = amountInput) { amountInput = it }) и для рассчета чаевых(val tip = calculateTip(amount)))для этого его инициируют вне применяемых функции,
    // а в функции где оно должно применяться передают в качестве аргументов
    @Composable
    private fun TipTimeScreen() {

        //сохранение состояния для рекомпозиции
        var amountInput by remember {
            mutableStateOf("")
        }
        // преобразовать данные в дабл или вывести значение по умолчанию
        val amount = amountInput.toDoubleOrNull() ?: 0.0
        var tipInput by remember {
            mutableStateOf("")
        }
        val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

        //состояние switch
        var roundUp by remember {
            mutableStateOf(false)
        }

        // рассчет чаевых
        val tip = calculateTip(amount, tipPercent, roundUp)

        //для работы с клавиатурой
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier.padding(32.dp),
            //спейсер по умолчанию среди вьюх в столбце
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.calculate_tip),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditNumberField(
                R.string.bill_amount,
                value = amountInput,
                onValueChange = { amountInput = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    //указывает, что при нажатии на ввод перейти на другой editText
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            EditNumberField(
                label = R.string.how_was_the_service,
                value = tipInput,
                onValueChange = { tipInput = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    //указывает, что при нажатии на ввод скрыть клавиатуру
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
            RoundTheTipRow(roundUp = roundUp, onRoundUpChange = { roundUp = it })
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                //передача в аргументы строкового ресурса "Tip amount: %s" tip. %s заменяется на tip
                text = stringResource(id = R.string.tip_amount, tip),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun EditNumberField(
        @StringRes label: Int,
        value: String,
        onValueChange: (String) -> Unit,
        keyboardOptions: KeyboardOptions,
        keyboardActions: KeyboardActions,
        modifier: Modifier = Modifier
    ) {
        TextField(
            //отображение в editText
            value = value,
            //указываем куда считываем изменения из editText
            onValueChange = onValueChange,
            //пишем подсказку в editText и растягиваем editText на максимальную ширину
            label = {
                Text(
                    text = stringResource(id = label),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            //указываем тип отображения клавиатуры при тапе на editText
            keyboardOptions = keyboardOptions,
            //для работы с кнопкой ввод на клаве
            keyboardActions = keyboardActions,
            //указываем, что данные должны быть введенны в одну строку
            singleLine = true
        )
    }

    @Composable
    fun RoundTheTipRow(
        roundUp: Boolean,
        onRoundUpChange: (Boolean) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .size(48.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.round_up_this))
            Switch(
                checked = roundUp,
                onCheckedChange = onRoundUpChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                colors = SwitchDefaults.colors(uncheckedThumbColor = Color.DarkGray)
            )
        }
    }

    private fun calculateTip(amount: Double, tipPercent: Double, roundUp: Boolean): String {
        var tip = tipPercent / 100 * amount
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        //возвращает число в денежном формате
        return NumberFormat.getCurrencyInstance().format(tip)
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeLabTheme {
            TipTimeScreen()
        }
    }

}