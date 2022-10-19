package com.diu.swan.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diu.swan.test.ui.theme.TestTheme

class LoadingScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    LoaderView()
                }
            }
        }
    }
}

@Composable
fun LoaderView() {

    var  progress by remember { mutableStateOf(0f) }
    val animatedProgress: Float by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1500,
            easing = FastOutSlowInEasing,
        )
    )


    if(animatedProgress == 100f ){
        val context = LocalContext.current
        val intent = Intent(context, MotionTest::class.java)
        context.startActivity(intent)
    }


    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.Black),
        verticalArrangement = Arrangement.Center

    ) {

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.group
                ),
                contentDescription = "",

                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp),


                )
        }

        Spacer(modifier = Modifier.height(10.dp))



        LinearProgressIndicator(
            animatedProgress,
            color = Color(0xFFF59E0B),
            modifier = Modifier
                .width(
                    140.dp
                )
                .height(6.dp)
                .clip(RoundedCornerShape(size = 6.dp))
                .padding(
                ),

            )
        Spacer(modifier = Modifier.weight(1f))

        progress = 100f
//        LaunchedEffect(progress) {
//            progress = progress
//        }

    }


}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview2() {
    TestTheme {

        LoaderView()
    }
}