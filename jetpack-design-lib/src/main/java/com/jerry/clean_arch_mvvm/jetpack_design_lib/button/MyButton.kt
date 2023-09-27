package com.jerry.clean_arch_mvvm.jetpack_design_lib.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            //background color
            containerColor = MaterialTheme.colorScheme.secondary,
            //contentColor: Color = TextButtonTokens.LabelTextColor.toColor(), text color
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = MaterialTheme.shapes.extraSmall,
        contentPadding = PaddingValues(horizontal = ComposeLibConstants.Companion.SPACING_XXL, vertical = ComposeLibConstants.Companion.SPACING_S)
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