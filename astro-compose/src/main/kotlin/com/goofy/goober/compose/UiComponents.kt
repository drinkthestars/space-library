package com.goofy.goober.compose

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimatedFloat
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goofy.goober.api.model.Image
import com.goofy.goober.common.R
import com.goofy.goober.compose.theme.ErrorBg
import com.goofy.goober.compose.theme.ImageTitleBg
import com.goofy.goober.model.ImageResultsState
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun animatedOpacity(
    animation: AnimationSpec<Float>,
    visible: Boolean,
    onAnimationFinish: () -> Unit = {}
): AnimatedFloat {
    val animatedFloat = animatedFloat(if (!visible) 1f else 0f)
    onCommit(visible) {
        animatedFloat.animateTo(
            if (visible) 1f else 0f,
            anim = animation,
            onEnd = { reason, _ ->
                if (reason == AnimationEndReason.TargetReached) onAnimationFinish()
            }
        )
    }
    return animatedFloat
}

@Composable
internal fun SearchInputBar(
    queryState: TextFieldValue,
    onTextFieldChange: (TextFieldValue) -> Unit,
    onQueryClear: () -> Unit
) {
    Box(
        alignment = Alignment.Center,
        modifier = Modifier.padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SearchInput(queryState) {
            onTextFieldChange(it)
        }
        Image(
            modifier = Modifier.preferredSize(30.dp)
                .align(Alignment.CenterEnd)
                .padding(top = 6.dp, end = 12.dp)
                .clickable(onClick = onQueryClear),
            asset = vectorResource(R.drawable.ic_clear)
        )
    }
}

@OptIn(ExperimentalFocus::class)
@Composable
internal fun SearchInput(
    textState: TextFieldValue,
    onTextFieldChange: (TextFieldValue) -> Unit
) {
    remember { onTextFieldChange(textState) }
    OutlinedTextField(
        activeColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focus(),
        value = textState,
        onValueChange = onTextFieldChange
    )
}

@Composable
internal fun BoxScope.ImageSearchResultsOverlay(
    state: ImageResultsState
) {
    when {
        state.noResults -> {
            Column {
                Text(":(")
                Text("Nothing like that exists in the known universe...")
            }
        }
        state.isLoading -> {
            Box(
                alignment = Alignment.Center,
                modifier = Modifier
                    .preferredSize(80.dp)
                    .align(Alignment.Center)
                    .background(Color(0x77123d40)),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.preferredSize(40.dp),
                    color = Color.Cyan,
                )
            }
        }
        state.hasError -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(50.dp)
                    .background(ErrorBg)
                    .align(Alignment.BottomCenter),
                alignment = Alignment.Center
            ) {
                Text(
                    text = "Error loading images",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
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

@Composable
internal fun ImageLoadInProgress() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(50.dp),
        alignment = Alignment.Center
    ) {
        Text(text = "Loading...")
    }
}

@Composable
internal fun ImageLoadError() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(50.dp),
        alignment = Alignment.Center
    ) {
        Text(text = ":( Couldn't load")
    }
}
