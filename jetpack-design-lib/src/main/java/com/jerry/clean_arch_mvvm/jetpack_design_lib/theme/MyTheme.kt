package com.jerry.clean_arch_mvvm.jetpack_design_lib.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyColor.Companion.Black
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyColor.Companion.Pink80
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyColor.Companion.Purple40
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyColor.Companion.Purple80
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyColor.Companion.White

//Ref: https://m2.material.io/design/material-theming/implementing-your-theme.html#color
/*
background - The background color that appears behind scrollable content.
onBackground - Color used for text and icons displayed on top of the background color.
 */
private val darkColorScheme = darkColorScheme(
    background = Black,
    onBackground = White, //usually used at text

    primary = Black,
    onPrimary = White, //usually used at text

    secondary = Pink80,
    onSecondary = Purple80,
)

private val lightColorScheme = darkColorScheme(
    background = White,
    onBackground = Black, //usually used at text

    primary = White,
    onPrimary = Black, //usually used at text

    secondary = Purple80,
    onSecondary = Pink80,
)

@Composable
fun MyAppTheme(
    customerTheme: ColorScheme? = null,
    content: @Composable () -> Unit
) {
    val ourColorScheme = customerTheme
        ?: if (isSystemInDarkTheme()) {
            darkColorScheme
        } else {
            lightColorScheme
        }

    MaterialTheme(
        content = content,
        colorScheme = ourColorScheme,
        shapes = Shapes(),
        typography = Typography()
    )
}