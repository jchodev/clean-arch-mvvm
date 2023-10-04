package com.jerry.clean_arch_mvvm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.contentColorFor
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerry.clean_arch_mvvm.jetpack_design_lib.button.MyOnPrimaryButton
import com.jerry.clean_arch_mvvm.jetpack_design_lib.button.MyOnSecondButton
import com.jerry.clean_arch_mvvm.jetpack_design_lib.button.MyOnSecondButton2
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme
import com.jerry.clean_arch_mvvm.presentation.JetpackMVIMainActivity
import com.jerry.clean_arch_mvvm.presentation.JetpackMVVMMainActivity
import com.jerry.clean_arch_mvvm.ui.theme.CleanarchmvvmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        MyOnSecondButton(
                            text = "Play with Fragment",
                            onClick = {
                                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                                startActivity(intent)
                            }
                        )

                        MyOnSecondButton(
                            text = "Play with MVVM Jetpack",
                            onClick = {
                                val intent = Intent(this@MainActivity, JetpackMVVMMainActivity::class.java)
                                startActivity(intent)
                            }
                        )

                        MyOnSecondButton(
                            text = "Play with MVI Jetpack",
                            onClick = {
                                val intent = Intent(this@MainActivity, JetpackMVIMainActivity::class.java)
                                startActivity(intent)
                            }
                        )

//                        Box(
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .background(Color.Green)
//                                    .size(200.dp)
//                                    .clickable {
//
//                                    },
//
//                                ) {
//
//                            }
//
//                            Card(
//
//                                shape = RoundedCornerShape(5.dp),
//                                modifier = Modifier
//                                    .size(100.dp)
//                                    .clickable(
//                                        interactionSource = MutableInteractionSource(),
//                                        indication = null,
//                                        onClick = {
//
//                                        }
//                                    )
//                            ) {
//                                Text("In Card")
//                            }
//                        }
                    }
                }
            }
        }
    }
}

object RippleCustomTheme: RippleTheme {

    //Your custom implementation...
    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            Color.Yellow,
            lightTheme = true
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color.Black,
            lightTheme = true
        )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CleanarchmvvmTheme {
        Greeting("Android")
    }
}