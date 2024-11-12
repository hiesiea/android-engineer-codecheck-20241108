package jp.co.yumemi.android.code_check.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.R

@Composable
fun OwnerIcon(
    ownerIconUrl: String,
    modifier: Modifier = Modifier,
) {
    if (LocalInspectionMode.current) {
        // Preview 時
        Image(
            painter = painterResource(R.drawable.jetbrains),
            contentDescription = "ownerIconUrl",
            modifier = modifier,
        )
    } else {
        // 通常時
        AsyncImage(
            model = ownerIconUrl,
            contentDescription = "ownerIconUrl",
            modifier = modifier,
            error = painterResource(id = R.drawable.empty_image),
        )
    }
}

@Preview
@Composable
private fun OwnerIconPreview() {
    OwnerIcon(ownerIconUrl = "ownerIconUrl")
}
