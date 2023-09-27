package com.jerry.clean_arch_mvvm.jetpack_design_lib.dialog

import android.annotation.SuppressLint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyColor.Companion.Purple80
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyColor.Companion.PurpleGrey40


@Composable
fun MyDialog(
    //left button
    title: String,
    message: String = "",
    dismissStr: String = "Dismiss",
    okStr: String = "OK",

    //left button
    onDismissRequest: () -> Unit,
    //right button
    onOKRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        MyDialogUI(
            onDismissRequest = onDismissRequest,
            onOKRequest = onOKRequest,
            title = title,
            message = message,
            dismissStr = dismissStr,
            OKStr = okStr
        )
    }
}

//Layout
@Composable
fun MyDialogUI(
    modifier: Modifier = Modifier,
    //left button
    title: String,
    message: String = "",
    dismissStr: String = "Dismiss",
    OKStr: String = "OK",
    onDismissRequest: () -> Unit,
    //right button
    onOKRequest: () -> Unit
){
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        //elevation = 8.dp
    ) {
        Column(
            modifier.background(MaterialTheme.colorScheme.primary)) {

            //.......................................................................

            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis, //(three dots)
                    color = MaterialTheme.colorScheme.onPrimary
                )
                androidx.compose.material3.Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(Purple80),
                horizontalArrangement = Arrangement.SpaceAround) {

                //Left
                androidx.compose.material3.TextButton(onClick = onDismissRequest) {

                    androidx.compose.material3.Text(
                        dismissStr,
                        fontWeight = FontWeight.Bold,
                        color = PurpleGrey40,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }

                //Right
                androidx.compose.material3.TextButton(onClick = onOKRequest) {
                    androidx.compose.material3.Text(
                        OKStr,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(name="Custom Dialog")
@Composable
fun MyDialogUIPreview(){
    MyAppTheme {
        MyDialogUI(
            title = "this is title",
            message = "this is message",
            onDismissRequest = {},
            onOKRequest = {}
        )
    }
}