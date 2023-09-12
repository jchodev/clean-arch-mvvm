package com.jerry.clean_arch_mvvm.jetpack_design_lib.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme


@Composable
fun MyDefaultText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Preview(showBackground = true)
@Composable
fun MyDefaultTextPreview() {
    MyAppTheme {
        MyDefaultText(text = "this is test text")
    }
}

@Composable
fun MyTitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Preview(showBackground = true)
@Composable
fun MyTitleTextPreview() {
    MyAppTheme {
        MyTitleText(text = "this is test MyTitleTextPreview")
    }
}
