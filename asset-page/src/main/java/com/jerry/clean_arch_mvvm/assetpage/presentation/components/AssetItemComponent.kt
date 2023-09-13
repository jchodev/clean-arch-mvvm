package com.jerry.clean_arch_mvvm.assetpage.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.clean_arch_mvvm.jetpack_design_lib.constants.ComposeLibConstants
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme

@Composable
fun AssetItemComponent(
    assetsName: String,
    assetCode: String,
    assetPrice: String,
    assetId: String?,
    onAssetItemClick: (String) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.background(
        color = MaterialTheme.colorScheme.primary)
            .clickable {
                assetId?.let {
                    onAssetItemClick.invoke(it)
                }
            }
    ) {
        val maxWidth = this.maxWidth

        Column(modifier =
            Modifier
                .fillMaxWidth()
                .padding(ComposeLibConstants.SPACING_M)
        ) {

            //title
            Text(
                text = assetsName,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(ComposeLibConstants.SPACING_S))

            //code and price
            Row {
                //asset code
                Text(
                    text = assetCode,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontStyle = FontStyle.Italic
                    )
                )

                Spacer(modifier = Modifier.width(ComposeLibConstants.SPACING_M))

                //asset price
                Text(
                    text = assetPrice,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetItemComponentPreview() {
    MyAppTheme {
        AssetItemComponent(
            assetsName = "this is assetsName",
            assetCode = "this is assetCode",
            assetPrice = "this is assetPrice",
            assetId = "",
            onAssetItemClick = {}
        )
    }
}
