package com.goofy.goober.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.goofy.goober.api.model.Image
import com.goofy.goober.common.R
import com.goofy.goober.compose.theme.ErrorBg
import com.goofy.goober.model.AstroAction
import com.goofy.goober.model.ImageResultsScreenState
import com.goofy.goober.model.ImageResultsState

@Composable
internal fun ImageSearch(
    state: ImageResultsScreenState,
    onNavigate: (AstroAction) -> Unit,
    onSearch: (String) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(4.dp))
                SearchInputBar(
                    state.query,
                    onSearch = {
                        state.update(query = it)
                        onSearch(it)
                    },
                    onQueryClear = { state.update(query = "") }
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
                ) {
                    itemsIndexed(
                        items = state.imageResultsState.images,
                        itemContent = { _, item ->
                            ImageResultItem(item) { onNavigate(AstroAction.OpenDetails(it)) }
                        }
                    )
                }
            }
            ImageSearchResultsOverlay(state.imageResultsState)
        }
    }
}

@Composable
internal fun SearchInputBar(
    queryState: String,
    onSearch: (String) -> Unit,
    onQueryClear: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SearchInput(queryState) {
            onSearch(it)
        }
        Image(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterEnd)
                .padding(top = 6.dp, end = 12.dp)
                .clickable(onClick = onQueryClear),
            painter = painterResource(R.drawable.ic_clear),
            contentDescription = null
        )
    }
}

@Composable
private fun SearchInput(
    textState: String,
    onTextFieldChange: (String) -> Unit
) {
    OutlinedTextField(
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focusable(true),
        value = textState,
        onValueChange = onTextFieldChange
    )
}

@Composable
private fun BoxScope.ImageSearchResultsOverlay(
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
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .background(Color(0x77123d40)),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = Color.Cyan,
                )
            }
        }
        state.hasError -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(ErrorBg)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
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
private fun ImageResultItem(image: Image, onImageClick: (Image) -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable(onClick = { onImageClick(image) })
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
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
private fun BoxScope.ImageTitleContent(image: Image) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .align(Alignment.BottomStart),
        text = image.title,
        color = Color.White,
        style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 5f), fontSize = 16.sp),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun ImageContent(image: Image) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(275.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = image.thumbUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null
        )
    }
}
