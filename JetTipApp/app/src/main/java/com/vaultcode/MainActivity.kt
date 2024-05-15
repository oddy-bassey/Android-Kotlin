package com.vaultcode

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaultcode.components.InputField
import com.vaultcode.ui.theme.JetTipAppTheme
import com.vaultcode.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipAppTheme {
        MyApp {
            Text(text = "My first Kotlin app")
        }
    }
}

@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 130.0) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(modifier = Modifier.padding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total per Person",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = total,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun BillForm(modifier: Modifier = Modifier,
             onValueChange: (String) -> Unit = {}) {

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableFloatStateOf(0f)
    }

    val splitByState = remember {
        mutableIntStateOf(1)
    }

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = (1.dp), color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {

            InputField(modifier = Modifier.fillMaxWidth(),
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if(!validState) return@KeyboardActions

                    // ToDo - onvaluechanged
                    onValueChange(totalBillState.value.trim())
                    keyboardController?.hide()
                }
            )
            //if (validState) {
                Row(modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text("Split",
                        modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {
                        val range = IntRange(1, 100)

                        RoundIconButton(modifier = Modifier,
                            imageVector = Icons.Default.Remove,
                            onClick = {
                                if(splitByState.intValue > 1) {
                                    splitByState.value = splitByState.intValue - 1
                                } else 1
                            })

                        Text(text = "${splitByState.intValue}",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp))

                        RoundIconButton(modifier = Modifier,
                            imageVector = Icons.Default.Add,
                            onClick = {
                                if(splitByState.intValue < range.last) {
                                    splitByState.value = splitByState.intValue + 1
                                }
                            })
                    }
                }
            // Tip Row
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 6.dp)){
                Text(text = "Tip",
                modifier = Modifier.align(Alignment.CenterVertically))

                Spacer(modifier = Modifier.width(200.dp))

                Text(text = "$33.00",
                    modifier = Modifier.align(Alignment.CenterVertically))
            }

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "33%")

                Spacer(modifier = Modifier.height(14.dp))

                Slider(value = sliderPositionState.value,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    onValueChange = {newValue ->
                        sliderPositionState.value = newValue
                        Log.d("Slider", "BillForm: $newValue")
                    },
                    steps = 5)
            }
//            } else {
//                Box() {}
//            }
        }
    }
}

@Preview
@Composable
fun MainContent() {
    Column(modifier = Modifier.padding(all = 12.dp)) {
        TopHeader()
        BillForm() {billAmount ->
            Log.d("AMT", "MainContent: $billAmount")
        }
    }
}