package com.jerry.clean_arch_mvvm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.clean_arch_mvvm.jetpack_design_lib.button.MyOnPrimaryButton
import com.jerry.clean_arch_mvvm.jetpack_design_lib.button.MyOnSecondButton
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
                    }
                }
            }
        }
    }
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