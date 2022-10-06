package com.diu.swan.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diu.swan.test.ui.theme.TestTheme
import kotlin.math.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Anim()
                }
            }
        }
    }
}

@Composable
fun Anim() {
    val animationState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .background(color = Color.Black)
        ) {
            Rocket(
                isRocketEnabled = animationState.value,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                animationState
            )


            Image(
                painter = painterResource(id = R.drawable.islans),
                contentDescription = "",
                modifier =
                Modifier
                    .width(50.dp)
                    .offset(
                        y = maxHeight / 2,
                        x = (maxWidth / 3)  - 80.dp
                    )
            )

            Image(
                painter = painterResource(id = R.drawable.islans),
                contentDescription = "",
                modifier =
                Modifier
                    .width(50.dp)
                    .offset(
                        y = maxHeight / 2 - 30.dp,
                        x = (maxWidth / 3) * 2 - 50.dp
                    )
            )


            Image(
                painter = painterResource(id = R.drawable.islans),
                contentDescription = "",
                modifier =
                Modifier
                    .width(50.dp)
                    .offset(
                        y = maxHeight / 2,
                        x = (maxWidth) - 80.dp
                    )
            )

//
//           Row(
//               Modifier.fillMaxSize(),
//               horizontalArrangement = Arrangement.SpaceEvenly,
//               verticalAlignment = Alignment.CenterVertically
//           ) {
//               Image(painter = painterResource(id = R.drawable.islans)
//                   , contentDescription = ""  , modifier =
//                   Modifier )
//
//
//               Image(painter = painterResource(id = R.drawable.islans)
//                   , contentDescription = ""  , modifier =
//                   Modifier)
//
//
//               Image(painter = painterResource(id = R.drawable.islans)
//                   , contentDescription = ""  , modifier =
//                   Modifier)
//           }


            LaunchButton(
                animationState = animationState.value,
                onToggleAnimationState = { animationState.value = !animationState.value }
            )
        }

    }
}


@Composable
fun Rocket(
    isRocketEnabled: Boolean,
    maxWidth: Dp,
    maxHeight: Dp,
    animationState: MutableState<Boolean>
) {
    val resource: Painter
    var modifier: Modifier
    val rocketSize = 50.dp
    val intialXoffset = remember {
        mutableStateOf(maxWidth / 3 - (rocketSize + 30.dp))
    }
    val intialyoffset = remember {
        mutableStateOf(((-5).dp))
    }

    modifier = Modifier.offset(
        y = intialyoffset.value,
        x = intialXoffset.value
    )

    if (!isRocketEnabled) {
        resource = painterResource(id = R.drawable.rocket_intial)

    } else {
        val infiniteTransition = rememberInfiniteTransition()

        val engineState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = LinearEasing
                )
            )
        )
        val startX = .2f
        val endX = .8f
        val topY = .2f
        val bottomY = .4f

        val start = maxWidth * startX
        val end = maxWidth * endX
        val top =  maxHeight / 2 - 30.dp  //maxHeight * topY
        val bottom = maxHeight / 2   // maxHeight * bottomY


        val yPositionState = infiniteTransition.animateFloat(

            initialValue =  top.value,
            targetValue = bottom.value,
            animationSpec = infiniteRepeatable(

                animation = tween(

                    durationMillis = 2000,
                    easing = LinearEasing
                )
            )
        )

        val xPositionState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(

                animation = tween(

                    durationMillis = 2000,
                    easing = LinearEasing
                )
            )
        )

        val fstDistance = ((maxWidth / 3) * 2 - 50.dp).value -
                ((maxWidth / 3) - 50.dp).value

        if (xPositionState.value > 0.98f) {
            animationState.value = !animationState.value
            intialyoffset.value =  getY(((maxWidth / 3 - (rocketSize)) + (maxWidth / 2 - (rocketSize)) * xPositionState.value).value.toInt(), ((maxWidth / 3)- 90.dp).value , fstDistance ).dp
            intialXoffset.value = (maxWidth / 3 - (rocketSize)) + (maxWidth / 2 - (rocketSize)) * xPositionState.value
        }

        resource = if (engineState.value <= .5f) {
            painterResource(id = R.drawable.rocket1)
        } else {
            painterResource(id = R.drawable.rocket2)
        }




        modifier = Modifier.offset(
            y =  getY(((maxWidth / 3 - (rocketSize)) + (maxWidth / 2 - (rocketSize)) * xPositionState.value).value.toInt(), ((maxWidth / 3) - 90.dp).value , fstDistance ).dp

            ,x = (maxWidth / 3 - (rocketSize)) + (maxWidth / 2 - (rocketSize)) * xPositionState.value


            //    x = (maxWidth - rocketSize) * xPositionState.value,
            //   y = (maxHeight - rocketSize) - (maxHeight - rocketSize) * xPositionState.value,
        )
    }



    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            modifier = modifier
                .width(rocketSize)
                .height(rocketSize)
                ,

            painter = resource,
            contentDescription = "A Rocket",
            contentScale = ContentScale.FillBounds
        )

    }
}


@Composable
fun LaunchButton(
    animationState: Boolean,
    onToggleAnimationState: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        if (animationState) {
            Button(
                onClick = onToggleAnimationState,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("STOP")
            }
        } else {
            Button(
                onClick = onToggleAnimationState,
            ) {
                Text("LAUNCH")
            }
        }
    }

}

const val TWO_PI = 2 * PI
val getY: (Int, Float, Float) -> Float = { x, amplitude, period ->
    //Log.d("TAG", ": $x   ${ (sin((x) * TWO_PI / period)).toFloat()}" )
    (sin((x) * TWO_PI / (period/1)) * (amplitude)).toFloat()
}

val gethalfY: (Float, Float, Float) -> Float = { x, a, b ->
    Log.d("TAG", ": $x   ${ 3*(sin((x/2)))}" )
    sqrt((b*b)-(x*x))
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestTheme {
        Anim()
    }
}
