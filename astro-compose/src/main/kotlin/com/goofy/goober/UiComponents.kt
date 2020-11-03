package com.goofy.goober

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.goofy.goober.api.model.Image
import com.goofy.goober.ui.theme.ImageTitleBg
import dev.chrisbanes.accompanist.coil.CoilImage

@OptIn(ExperimentalFocus::class)
@Composable
internal fun SearchInput(onTextChange: (String) -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    TextField(
        shape = RoundedCornerShape(size = 3.dp),
        modifier = Modifier.padding(start = 12.dp, end = 12.dp).fillMaxWidth()
            .wrapContentHeight().focus(),
        value = textState,
        onValueChange = {
            textState = it
            onTextChange(it.text)
        }
    )
}

@Composable
internal fun ImageResultItem(image: Image, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth().preferredHeight(275.dp)) {
            Card(
                shape = RoundedCornerShape(size = 5.dp)
            ) {
                CoilImage(
                    contentScale = ContentScale.Crop,
                    data = image.thumbUrl,
                    fadeIn = true
                )
            }
            Text(
                text = image.title,
                color = Color.White,
                modifier = Modifier.fillMaxWidth().preferredHeight(50.dp)
                    .background(color = ImageTitleBg).align(Alignment.BottomCenter)
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().preferredHeight(8.dp))
    }
}
