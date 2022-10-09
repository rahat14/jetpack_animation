package com.diu.swan.test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diu.swan.test.ui.theme.TestTheme


class MotionTest : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val animationState = remember { mutableStateOf(false) }
                    val points = remember { mutableStateOf(0) }
                    val configuration = LocalConfiguration.current

                    val screenHeight = configuration.screenHeightDp.dp
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),

                        contentAlignment = Alignment.BottomCenter

                    ) {


                        LoadData(animationState, points)
                        QustionCard(animationState)


//                        Spacer(modifier = Modifier.height(30.dp))
//
//                        Row(modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//                            Text(text = "Points - ${points.value} ", color = Color.Black)
//                            Spacer(Modifier.weight(0.3f))
//                            Button(onClick = {
//                                if(points.value  != 60){
//                                    animationState.value = !animationState.value
//                                }
//
//                            }) {
//                                Text(text = "Jump")
//                            }
//                            Spacer(Modifier.weight(0.8f))
//                        }


                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QustionCard(animationState: MutableState<Boolean>) {
    Card(

        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),

        containerColor = Color(0x856808FF),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        contentColor = Color(0x856808FF),

        ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(
                "This is a question of the game ? ",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                    .background(color = Color.White)
                    .padding(vertical = 8.dp)
                    .clickable {

                        animationState.value = !animationState.value

                    },
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    "Option 1 ",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1.0f)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                        .background(color = Color.Magenta)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    "Option 2 ",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1.0f)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                        .background(color = Color.Magenta)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    "Option 3 ",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1.0f)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                        .background(color = Color.Magenta)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    "Option 4 ",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1.0f)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                        .background(color = Color.Magenta)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


fun getPixelFromDP(density: Density, pixl: Int) {
    return density.run { pixl.dp.toPx() }
}

fun getDPFromPixel(density: Density, pixl: Int) {
    return density.run { pixl.toDp() }
}

@Composable
fun LoadData(
    animState: MutableState<Boolean>,
    points: MutableState<Int>
) {

    var invalidations by remember {
        mutableStateOf(0)
    }
    var JumpedPlatfrom by remember {
        mutableStateOf(6)
    }
    // initial location of the jump the user is in.

    val LD = LocalDensity.current
    val infiniteTransition = rememberInfiniteTransition()
    val index = remember {
        mutableStateOf(5)
    }
    val option = BitmapFactory.Options()
    option.apply {
        inPreferredConfig = Bitmap.Config.ARGB_8888
    }
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.80f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        )
    )

    val imageBitmapp = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.platfrom,
        option
    )

    val coins_image = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.coins,
        option
    )


    val test_image = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.rocket1,
        option
    )


    val man_image = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.man,
        option
    )


    val platfrom_width = LD.run { 130.dp.toPx() }
    val platfrom_height = LD.run { 60.dp.toPx() }

    val man_width = LD.run { 50.dp.toPx() }
    val man_height = LD.run { 100.dp.toPx() }

    val coins_width = LD.run { 30.dp.toPx() } * scale
    val coins_height = LD.run { 30.dp.toPx() }

    val imageBitmap = Bitmap.createScaledBitmap(
        imageBitmapp,
        platfrom_width.toInt(), platfrom_height.toInt(), true
    ).asImageBitmap()

    val manBitmap = Bitmap.createScaledBitmap(
        man_image,
        man_width.toInt(), man_height.toInt(), true
    ).asImageBitmap()

    val testBitmap = Bitmap.createScaledBitmap(
        test_image,
        man_width.toInt(), man_height.toInt(), true

    ).asImageBitmap()

    val CoinBitmap = Bitmap.createScaledBitmap(
        coins_image,
        coins_width.toInt(), coins_width.toInt(), true
    ).asImageBitmap()


    val ytarget = if (animState.value) 1f else {
        0f
    }

    val heightarget = if (animState.value) 5f else {
        0f
    }

    val yPositionState = infiniteTransition.animateFloat(

        initialValue = 0.0f,
        targetValue = ytarget,
        animationSpec = infiniteRepeatable(

            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            )
        )
    )


    val HightPositionState = infiniteTransition.animateFloat(

        initialValue = 0.0f,
        targetValue = heightarget,
        animationSpec = infiniteRepeatable(

            animation = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            )
        )
    )


    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
            .padding(10.dp)
            .padding(bottom = 50.dp),
    ) {
        invalidations.let { inv ->
            //Draw Composables
            Log.d("PRINT", "LoadData: printing $inv")
            val canvasWidth = size.width
            val canvasHeight = size.height

            var isLeft = false
            val listOfOffset: MutableList<Offset> = mutableListOf()



            for (i in 0..canvasHeight.toInt() step (canvasHeight / 7).toInt()) {

                if (i != 0) {

                    if (isLeft) {
//                        drawLine(
//                            start = Offset(x = 100f, y = i.toFloat()),
//                            end = Offset(x = 220f, y = i.toFloat()),
//                            color = Color.Blue,
//                            strokeWidth = 10.0F
//                        )

                        drawImage(
                            image = imageBitmap,
                            topLeft = Offset(
                                x = 160f - (platfrom_width / 2),
                                y = i.toFloat() - (platfrom_height / 2)
                            )
                        )


                        listOfOffset.add(Offset(x = 160f, y = i.toFloat()))

                    } else {

//                        drawLine(
//                            start = Offset(x = canvasWidth - 220, y = i.toFloat()),
//                            end = Offset(x = canvasWidth - 100, y = i.toFloat()),
//                            color = Color.Blue,
//                            strokeWidth = 10.0F
//                        )

                        drawImage(
                            image = imageBitmap,
                            topLeft = Offset(
                                x = canvasWidth - (platfrom_width / 2) - (220 - 60),
                                y = i.toFloat() - (platfrom_height / 2)
                            )
                        )

                        listOfOffset.add(Offset(x = canvasWidth - (220 - 60), y = i.toFloat()))
                        Log.d("TAG", "LoadData: ${canvasWidth - 220} , ${i.toFloat()}")
                    }

                    isLeft = !isLeft
                    println(i)

                }

            }

            var step = 0
            isLeft = false
            /*
             render coin
             */

            for (i in 0..canvasHeight.toInt() step (canvasHeight / 7).toInt()) {

                Log.d("PRINT", "LoadData: $JumpedPlatfrom")

                if (step in 1..5 && step < JumpedPlatfrom) {

                    if (isLeft) {


                        drawImage(

                            image = CoinBitmap,
                            topLeft = Offset(
                                x = 160f - (coins_width / 2),
                                y = i.toFloat() - (coins_height)
                            ),
                            alpha = scale
                        )


                      //  listOfOffset.add(Offset(x = 160f, y = i.toFloat()))

                    } else {


                        drawImage(
                            image = CoinBitmap,
                            topLeft = Offset(
                                x = canvasWidth - (coins_width / 2) - (220 - 60),
                                y = i.toFloat() - (coins_height)
                            ),
                            alpha = scale,

                            )


                     //   listOfOffset.add(Offset(x = canvasWidth - (220 - 60), y = i.toFloat()))
                        Log.d("TAG", "LoadData: ${canvasWidth - 220} , ${i.toFloat()}")
                    }

                    isLeft = !isLeft
                    println(i)

                }
                step += 1

            }


            var p1 = listOfOffset[index.value]
            var p2 = if (index.value != 0) {
                listOfOffset[index.value - 1]
            } else {
                listOfOffset[index.value]
            }


            var x = 0f
            var y = 0f

            val jumpOff = LD.run { 21.dp.toPx() }


            if (!animState.value) {
                x = p1.x
                y = p1.y

            } else {
                x = p1.x    //+ density_30
                y = p1.y    //- density_30

                x += ((p1.x - p2.x)) * yPositionState.value * - 1
                y += HightPositionState.value * - (jumpOff)

            }

            Log.d("Y", "LoadData: $canvasWidth ${p1.x} ${p2.x}")


            drawImage(
//                image = if (JumpedPlatfrom % 2 == 0) {
//                    manBitmap
//                } else manBitmap,
                image = manBitmap ,
                topLeft = Offset(
                    x = x - (man_width / 2),    //listOfOffset.last().x + 50,
                    y = y - man_height  //listOfOffset.last().y - 30
                ),

                )




            if (yPositionState.value > 0.99 && animState.value) {

                index.value = index.value - 1

                p1 = listOfOffset[index.value]
                p2 = if (index.value != 0) {
                    listOfOffset[index.value - 1]
                } else listOfOffset[index.value]


                x = p1.x
                y = p1.y

                JumpedPlatfrom -= 1
                points.value += 10

                animState.value = !animState.value
               // invalidations++


            }


//        drawLine(
//            start = p1,
//            end = p2,
//            color = Color.Red,
//            strokeWidth = 4.0f
//
//        )


//        drawArc(
//            color = Color.Red,
//            0f,
//            -180f,
//            false,
//            style = Stroke(2.dp.toPx(), cap = StrokeCap.Round),
//        )
            // Offset(100.0, 1530.0), Offset(804.0, 1836.0), Offset(100.0, 2142.0)]


        }

    }
}


@Composable
fun Pulsating(pulseFraction: Float = 1.2f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultxPreview() {
    val animationState = remember { mutableStateOf(false) }

    QustionCard(animationState)
}



