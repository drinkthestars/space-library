package com.goofy.goober

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goofy.goober.api.model.Image
import com.goofy.goober.ui.theme.ImageTitleBg
import dev.chrisbanes.accompanist.coil.CoilImage

@OptIn(ExperimentalFocus::class)
@Composable
internal fun SearchInput(onTextChange: (String) -> Unit) {
    var textState by remember {
        onTextChange("galaxy")
        mutableStateOf(TextFieldValue("galaxy"))
    }
    OutlinedTextField(
        activeColor = Color.White,
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .focus(),
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
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            elevation = 5.dp,
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(size = 5.dp)
        ) {
            Box {
                ImageContent(image)
                ImageTitleContent(image)
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(18.dp)
                .background(MaterialTheme.colors.background)
        )
    }
}

@Composable
private fun BoxScope.ImageTitleContent(image: Image) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(50.dp)
            .background(ImageTitleBg)
            .align(Alignment.BottomStart),
        alignment = Alignment.CenterStart
    ) {
        Text(
            text = image.title,
            color = Color.White,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun ImageContent(image: Image) {
    Box(
        modifier = Modifier.fillMaxWidth().preferredHeight(275.dp),
        alignment = Alignment.Center
    ) {
        CoilImage(
            modifier = Modifier.fillMaxWidth().preferredHeight(275.dp),
            contentScale = ContentScale.Crop,
            data = image.thumbUrl,
            fadeIn = true
        )
    }
}
