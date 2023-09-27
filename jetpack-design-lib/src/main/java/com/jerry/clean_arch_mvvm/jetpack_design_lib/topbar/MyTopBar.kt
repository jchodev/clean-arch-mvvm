package com.jerry.clean_arch_mvvm.jetpack_design_lib.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.clean_arch_mvvm.jetpack_design_lib.text.MyTitleText
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String = "",
    visibleState: State<Boolean?>,
    onClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary),
        title = {
            MyTitleText(text = title)
        },
        navigationIcon = {
            AnimatedVisibility(
                visible = visibleState.value ?: false,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = {
                        onClick()
                    }
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },

        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MyTopBarPreview(){
    MyAppTheme {
        MyTopBar(title = "this is title",
            onClick = { },
            visibleState = remember { mutableStateOf(true) }
        )
    }
}

