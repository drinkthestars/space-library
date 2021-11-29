package com.goofy.goober.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goofy.goober.common.R
import com.goofy.goober.compose.theme.SplashBg
import com.goofy.goober.model.AstroNavAction

@Composable
internal fun Splash(onNavigate: (AstroNavAction) -> Unit) {
    Surface(color = SplashBg, modifier = Modifier.fillMaxSize()) {
        SplashContent(onNavigate)
    }
}

@Composable
internal fun SplashContent(
    onAction: (AstroNavAction) -> Unit
) {
    val alpha = fadeOutAfter(duration = 1_700) {
        onAction(AstroNavAction.ToImageSearchResults)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { this.alpha = alpha.value }
    ) {
        Image(
            painter = painterResource(R.drawable.splash),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Space Library",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 110.dp)
                    .align(Alignment.TopCenter)
            )
            Text(
                "nasa.gov/images",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color(0xAAFFFFFF),
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
