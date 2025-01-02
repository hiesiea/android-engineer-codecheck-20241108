package jp.co.yumemi.android.codecheck.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun OwnerIcon(
    ownerIconUrl: String,
    modifier: Modifier = Modifier,
) {
    var isError by remember { mutableStateOf(false) }

    if (LocalInspectionMode.current) {
        // Preview 時
        Image(
            painter = painterResource(id = R.drawable.jetbrains),
            contentDescription = "OwnerIcon",
            modifier = modifier,
        )
    } else {
        // 通常時
        if (isError) {
            Box(modifier = modifier.background(Color.LightGray))
        } else {
            AsyncImage(
                model = ownerIconUrl,
                contentDescription = "OwnerIcon",
                modifier = modifier,
                onError = {
                    isError = true
                },
            )
        }
    }
}

@Preview
@Composable
private fun OwnerIconPreview() {
    OwnerIcon(ownerIconUrl = "ownerIconUrl")
}
