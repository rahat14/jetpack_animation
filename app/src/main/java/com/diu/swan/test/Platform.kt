package com.diu.swan.test

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Platform(
    question: Int = 0,
    stage: MutableState<Int> = mutableStateOf(0),
    drawNow: Boolean,
    manPosition: MutableState<Offset>,
    previousPosition: MutableState<Offset>
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(modifier = Modifier.size(100.dp)) {
        Image(
            painter = painterResource(id = R.drawable.platfrom),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(210.dp)
                .align(Alignment.BottomCenter)
        )

        Text(text = question.toString())

        AnimatedContent(
            targetState = stage.value == question && drawNow,
            modifier = Modifier
                .height(65.dp)
                .width(50.dp)
                .align(Alignment.Center)
                .offset(y = (-14).dp)
        ) { targetState ->
            if (targetState) {
                Image(
                    painter = painterResource(id = R.drawable.man),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (question % 2 != 0) {
                                rotationY = 180f
                            }
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun PlatformView() {
    MaterialTheme {
        Platform(
            drawNow = true,
            manPosition = remember { mutableStateOf(Offset(0f, 0f)) },
            previousPosition = remember { mutableStateOf(Offset(0f, 0f)) }
        )
    }
}
