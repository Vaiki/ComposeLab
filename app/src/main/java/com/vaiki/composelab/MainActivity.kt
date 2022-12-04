package com.vaiki.composelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaiki.composelab.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                // A surface container using the 'background' color from the theme
                LemonadeApp()

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun LemonadeApp() {
        CreateLemonade(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )

    }

    @Composable
    fun CreateLemonade(modifier: Modifier = Modifier) {

        val onImageClick: () -> Unit
        var result by remember { mutableStateOf(1) }
        var squeezeCount by remember {
            mutableStateOf(0)
        }
        var message = ""
        val imageRes: Int
        when (result) {
            1 -> {
                imageRes = R.drawable.lemon_tree
                message = stringResource(id = R.string.first_lay)
                onImageClick = {
                    result = 2
                    squeezeCount = (2..4).random()
                }
            }
            2 -> {
                imageRes = R.drawable.lemon_squeeze
                message = stringResource(id = R.string.second_lay)
                onImageClick = {
                    // уменьшает до тех пор пока не станет 0 и тогда переводит на другое окно
                    squeezeCount--
                    if (squeezeCount == 0) {
                        result = 3
                    }
                }
            }

            3 -> {
                imageRes = R.drawable.lemon_drink
                message = stringResource(id = R.string.third_lay)
                onImageClick = {
                    result = 4
                }
            }

            else -> {
                imageRes = R.drawable.lemon_restart
                message = stringResource(id = R.string.fourth_lay)
                onImageClick = {
                    result = 1
                }
            }
        }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = message, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(onClick = onImageClick)
                    .border(
                        BorderStroke(2.dp, Color(105, 205, 216)),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}