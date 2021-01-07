package com.goofy.goober.compose

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//@Preview
//@Composable
//internal fun SearchInputBarPreview() {
//    var querySate = remember { TextFieldValue("preview")}
//    Column(modifier = Modifier.fillMaxSize()) {
//        Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(12.dp))
//        SearchInputBar(
//            querySate,
//            onTextFieldChange = {
//                querySate = it
//            },
//            onQueryClear = { querySate = TextFieldValue("") }
//        )
//        Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(12.dp))
//    }
//}

@Preview
@Composable
internal fun SplashPreview() {
    SplashContent(animatedFloat(initVal = 1f))
}
