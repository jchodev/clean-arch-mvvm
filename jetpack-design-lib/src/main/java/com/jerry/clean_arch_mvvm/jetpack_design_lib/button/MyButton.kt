package com.jerry.clean_arch_mvvm.jetpack_design_lib.button

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.clean_arch_mvvm.jetpack_design_lib.constants.ComposeLibConstants
import com.jerry.clean_arch_mvvm.jetpack_design_lib.text.MyDefaultText
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme
import java.util.Locale

@Composable
fun MyOnPrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            //background color
            containerColor = MaterialTheme.colorScheme.primary,
            //contentColor: Color = TextButtonTokens.LabelTextColor.toColor(), text color
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.extraSmall,
        contentPadding = PaddingValues(horizontal = ComposeLibConstants.Companion.SPACING_XXL, vertical = ComposeLibConstants.Companion.SPACING_S)
    ) {
        MyDefaultText(text = text.uppercase(Locale.ROOT))
    }
}

@Composable
fun MyOnSecondButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = onClick,
        modifier = modifier.indication(
            interactionSource = interactionSource,
            indication = rememberRipple(
                color =  MaterialTheme.colorScheme.secondary,
            )
        ),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary, // Default button background color
            contentColor = Color.White // Default button text color
        ),
        interactionSource = interactionSource,
    ) {
        MyDefaultText(text = text.uppercase(Locale.ROOT))
    }
}

@Composable
fun MyOnSecondButton2(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = onClick,
        modifier = modifier.indication(
            interactionSource = interactionSource,
            indication = rememberRipple(
                color =  MaterialTheme.colorScheme.secondary,
            )
        ),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary, // Default button background color
            contentColor = Color.White // Default button text color
        ),
        interactionSource = interactionSource,
    ) {
        MyDefaultText(text = text.uppercase(Locale.ROOT))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyOnPrimaryButton(text: String = "Text Primary titleMedium") {
    MyAppTheme {
        MyOnPrimaryButton(text = text) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyOnSecondButton(text: String = "Text Second titleMedium") {
    MyAppTheme {
        MyOnSecondButton(text = text) {}
    }
}