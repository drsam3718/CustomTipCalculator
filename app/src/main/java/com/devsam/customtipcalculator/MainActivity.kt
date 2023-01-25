package com.devsam.customtipcalculator

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.input.EditCommand
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devsam.customtipcalculator.ui.theme.CustomTipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTipCalculatorTheme {
                Surface(modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.background
                ) {
                    TipWithPercentAndRound()

                }
            }
        }
    }
}

@Composable
fun TipWithPercentAndRound(){
//    states defined here
//    var amountEntered by remember {
//        mutableSetOf("")
//    }
    val focusManager = LocalFocusManager.current
    var amountEntered by remember {
        mutableStateOf("")
    }
    var tipEntered by remember {
        mutableStateOf("")
    }
    var roundOrNot by remember {
        mutableStateOf(false)
    }
    var amount = amountEntered.toDoubleOrNull() ?: 0.0
    var tipPercentage = tipEntered.toDoubleOrNull() ?: 0.0
    var tip = CalculateFinalTip(amount = amount, tipPercentage = tipPercentage, doRound = roundOrNot )

//    composables starting to be declared here
    Column (
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        Text(
            text = stringResource(id = R.string.title),
//            modifier = Modifier.size(15.dp),
//            fontWeight = FontWeight.Bold,
            fontSize = 32.sp

            )

        Spacer(modifier = Modifier.height(10.dp))

//        AddAmountOfBillTextField(amount = amountEntered, onValueChange = { amountEntered = it })
        EditTextField(
            label = R.string.bill_amount_label,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = amountEntered,
            onValueChange = {amountEntered = it}
        )
        
        Spacer(modifier = Modifier.height(10.dp))

//        AddTipPercentTextField(tipPercent = tipEntered, onValueChange = {tipEntered = it})
        EditTextField(
            label = R.string.tip_amount_label,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            value = tipEntered,
            onValueChange = {tipEntered = it}
        )
        
        Spacer(modifier = Modifier.height(10.dp))
        
        RoundUp(roundOrNot = roundOrNot, onRoundUpChanged = {roundOrNot = it})
        
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = stringResource(id = R.string.final_tip, tip),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold)
    }
}



//composable defined here
@Composable
fun EditTextField(
    label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = label), modifier = Modifier.fillMaxWidth())  },
        keyboardOptions =keyboardOptions
    )

}

@Composable
fun RoundUp(
    roundOrNot: Boolean,
    onRoundUpChanged: (Boolean) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.round_up_toggle_button_label))
        Switch(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End),
            checked = roundOrNot,
            onCheckedChange = onRoundUpChanged,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            )
        )
    }
}



//functions are defined here
fun CalculateFinalTip(amount: Double, tipPercentage: Double , doRound: Boolean = false): String{
    var tip = amount*tipPercentage/100
    if(doRound){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CustomTipCalculatorTheme {
        TipWithPercentAndRound()
    }
}