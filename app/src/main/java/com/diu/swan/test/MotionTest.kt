package com.diu.swan.test

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.os.postDelayed
import com.diu.swan.test.ui.theme.TestTheme


class MotionTest : ComponentActivity() {

    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var wrongMediaPlayer: MediaPlayer
    var JumpedPlatfrom = mutableStateOf(6)
    var isLastPlatfrom = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
                ) {

                    val context = LocalContext.current
                    val activity = (LocalContext.current as? Activity)
                    mMediaPlayer = MediaPlayer()
                    wrongMediaPlayer = MediaPlayer.create(context, R.raw.fail)
                    val animationState = remember { mutableStateOf(false) }
                    val isWrongState = remember { mutableStateOf(false) }
                    val isSkippingState = remember { mutableStateOf(false) }
                    val currentDeath = remember { mutableStateOf(3) }
                    val millisInFuture: Long = 5 * 1000 // TODO: get actual value

                    val timeData = remember {
                        mutableStateOf(0L)
                    }

                    countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            Log.d("TAG", "onTick: ")
                            timeData.value = millisUntilFinished
                        }

                        override fun onFinish() {

                            if (currentDeath.value != 0) {

//                                wrongMediaPlayer.start()
//                                currentDeath.value = currentDeath.value - 1
//                                isWrongState.value = !isWrongState.value
                                animationState.value = true
                                isSkippingState.value = true

                            }


                        }
                    }


                    val showDialouge = remember { mutableStateOf(0) } // 1 wrong 2// success

                    val points = remember { mutableStateOf(0) }
                    val configuration = LocalConfiguration.current
                    val trans = rememberInfiniteTransition()
                    
                    Box(
                        modifier = Modifier.fillMaxWidth(),

                        contentAlignment = Alignment.BottomCenter

                    ) {


                        Image(

                            painter = painterResource(id = R.drawable.bg),
                            contentDescription = null,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop,


                            )




                        LoadData(
                            animationState,
                            points,
                            isWrongState,
                            showDialouge,
                            currentDeath,
                            isSkippingState
                        )



                        QuestionCard(
                            animationState,
                            isWrongState,
                            timeData,
                            currentDeath,
                            isSkippingState
                        )


                        Row(
                            modifier = Modifier
                                .align(
                                    Alignment.TopStart
                                )
                                .padding(
                                    12.dp
                                )
                        ) {
                            AnimatedVisibility(visible = currentDeath.value >= 1) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.life
                                    ), contentDescription = "", modifier = Modifier.size(18.dp)
                                )

                            }
                            AnimatedVisibility(visible = currentDeath.value >= 2) {
                                Image(
                                    painter = painterResource(id = R.drawable.life),
                                    contentDescription = "",
                                    modifier = Modifier.size(18.dp)
                                )

                            }
                            AnimatedVisibility(visible = currentDeath.value == 3) {
                                Image(
                                    painter = painterResource(id = R.drawable.life),
                                    contentDescription = "",
                                    modifier = Modifier.size(18.dp)
                                )

                            }
                        }

                        val hide = {
                            showDialouge.value = 0
                        }
                        val replay = {
                            showDialouge.value = 0
                            activity?.finish()
                            context.startActivity(Intent(context, MotionTest::class.java))
                        }


                        if (showDialouge.value == 1) {
                            countDownTimer.cancel()
                            animationState.value = false
                            isWrongState.value = false
                            SimpleAlertDialog(
                                "You Failed In This Simple Game...", hide, replay
                            )
                        } else if (showDialouge.value == 2) {
                            animationState.value = false
                            animationState.value = false
                            isWrongState.value = false
                            countDownTimer.cancel()
                            SimpleAlertDialog(
                                "Ye Ye You Won This Simple Game. SO ?", hide, replay
                            )
                        }

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


    @Composable
    fun SimpleAlertDialog(
        title: String = "Ye Ye You Won This Simple Game. SO ?",
        onDismiss: () -> Unit,
        onConfirm: () -> Unit
    ) {

        AlertDialog(properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        ), onDismissRequest = { onDismiss() }, confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) { Text(text = "Replay") }
        }, title = { Text(text = "Oh No!!!") }, text = { Text(text = "$title") })
    }


    @Composable
    fun LoadData(
        animState: MutableState<Boolean>,
        points: MutableState<Int>,
        isWrongState: MutableState<Boolean>,
        showDialouge: MutableState<Int>,
        currentDeath: MutableState<Int>,
        isSkipping: MutableState<Boolean>
    ) {

        var invalidations by remember {
            mutableStateOf(0)
        }
        JumpedPlatfrom = remember {
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
            initialValue = 0.90f, targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween(400), repeatMode = RepeatMode.Reverse
            )
        )

        val imageBitmapp = BitmapFactory.decodeResource(
            LocalContext.current.resources, R.drawable.platfrom, option
        )

        val coins_image = BitmapFactory.decodeResource(
            LocalContext.current.resources, R.drawable.coins, option
        )

        val man_image = BitmapFactory.decodeResource(
            LocalContext.current.resources, R.drawable.man, option
        )


        val platfrom_width = LD.run { 130.dp.toPx() }
        val platfrom_height = LD.run { 60.dp.toPx() }

        val man_width = LD.run { 50.dp.toPx() }
        val man_height = LD.run { 100.dp.toPx() }

        val coins_width = LD.run { 30.dp.toPx() } * scale
        val coins_height = LD.run { 30.dp.toPx() }

        val imageBitmap = Bitmap.createScaledBitmap(
            imageBitmapp, platfrom_width.toInt(), platfrom_height.toInt(), true
        ).asImageBitmap()

        val manBitmap = Bitmap.createScaledBitmap(
            man_image, man_width.toInt(), man_height.toInt(), true
        ).asImageBitmap()


        val CoinBitmap = Bitmap.createScaledBitmap(
            coins_image, coins_width.toInt(), coins_width.toInt(), true
        ).asImageBitmap()


        val ytarget = if (animState.value) 2f else {
            0f
        }

        val heightarget = if (animState.value) 5f else {
            0f
        }

        val yPositionState = infiniteTransition.animateFloat(

            initialValue = 0.0f, targetValue = ytarget, animationSpec = infiniteRepeatable(

                animation = tween(
                    durationMillis = 2000, easing = LinearEasing
                )
            )
        )

        val shakeState = infiniteTransition.animateFloat(
            initialValue = if (!animState.value) {
                -8f
            } else {
                0f
            }, targetValue = if (!animState.value) {
                8f
            } else {
                0f
            }, animationSpec = infiniteRepeatable(

                animation = tween(
                    durationMillis = 600, easing = LinearEasing
                ), repeatMode = RepeatMode.Reverse
            )
        )


        val HightPositionState = infiniteTransition.animateFloat(

            initialValue = 0.0f, targetValue = heightarget, animationSpec = infiniteRepeatable(

                animation = tween(
                    durationMillis = 2000, easing = LinearOutSlowInEasing
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
                .background(color = Color.Transparent)
                .padding(10.dp)
                .padding(bottom = 100.dp),
        ) {

            //Draw Composables
            // Log.d("PRINT", "LoadData: printing $inv")
            val canvasWidth = size.width
            val canvasHeight = size.height

            var isLeft = false
            val listOfOffset: MutableList<Offset> = mutableListOf()


            var shake = 0f
            var nowStep = 0
            for (i in 0..canvasHeight.toInt() step (canvasHeight / 7).toInt()) {


//                if (JumpedPlatfrom.value == nowStep + 1) {
//
//                    shake += shakeState.value
//                }

                if (i != 0) {

                    if (isLeft) {

//                        drawLine(
//                            start = Offset(x = 100f, y = i.toFloat()),
//                            end = Offset(x = 220f, y = i.toFloat()),
//                            color = Color.Blue,
//                            strokeWidth = 10.0F
//                        )

                        drawImage(
                            image = imageBitmap, topLeft = Offset(
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
                            image = imageBitmap, topLeft = Offset(
                                x = canvasWidth - (platfrom_width / 2) - (220 - 60) + shake,
                                y = i.toFloat() - (platfrom_height / 2)
                            )
                        )

                        listOfOffset.add(Offset(x = canvasWidth - (220 - 60), y = i.toFloat()))
                        Log.d("TAG", "LoadData: ${canvasWidth - 220} , ${i.toFloat()}")
                    }

                    isLeft = !isLeft

                    nowStep += 1

                }

            }

            var step = 0
            isLeft = false
            for (i in 0..canvasHeight.toInt() step (canvasHeight / 7).toInt()) {

                Log.d("PRINT", "Jumped: $JumpedPlatfrom")

                if (step in 1..6 && step < JumpedPlatfrom.value) {

                    if (isLeft) {


                        drawImage(

                            image = CoinBitmap, topLeft = Offset(
                                x = 160f - (coins_width / 2), y = i.toFloat() - (coins_height)
                            ), alpha = scale
                        )


                        listOfOffset.add(Offset(x = 160f, y = i.toFloat()))

                    } else {


                        drawImage(
                            image = CoinBitmap,
                            topLeft = Offset(
                                x = canvasWidth - (coins_width / 2) - (220 - 60),
                                y = i.toFloat() - (coins_height)
                            ),
                            alpha = scale,

                            )


                        listOfOffset.add(Offset(x = canvasWidth - (220 - 60), y = i.toFloat()))
                        Log.d("TAG", "LoadData: ${canvasWidth - 220} , ${i.toFloat()}")
                    }

                    isLeft = !isLeft


                }
                step += 1

            }


            if (index.value < 0) {
                animState.value = false
                return@Canvas
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


            if (isWrongState.value) {
                animState.value = true

                x = p1.x    //+ density_30
                y = p1.y    //- density_30

                x += (10) * yPositionState.value
                y += HightPositionState.value * 500
            }


            if (!animState.value) {
                x = p1.x
                y = p1.y

            }
            else if (animState.value && !isWrongState.value) {
                x = p1.x    //+ density_30
                y = p1.y    //- density_30


                x += ((p1.x - p2.x)) * yPositionState.value * -1
                y += HightPositionState.value * -(jumpOff)

            }

            // Log.d("Y", "LoadData: $canvasWidth ${p1.x} ${p2.x}")


            drawImage(
                image = manBitmap,
                topLeft = Offset(
                    x = x - (man_width / 2),    //listOfOffset.last().x + 50,
                    y = y - man_height - 12  //listOfOffset.last().y - 30
                ),

                )


            if (yPositionState.value > 0.98 && animState.value) {


                if (isWrongState.value) {

                    x = -100f
                    y = -50f


                    if (currentDeath.value != 0) {

//                        index.value = index.value
//
//                        p1 = listOfOffset[index.value]
//                        p2 = if (index.value != 0) {
//                            listOfOffset[index.value - 1]
//                        } else listOfOffset[index.value]


                        x = p1.x
                        y = p1.y


                        // currentDeath.value = currentDeath.value - 1

                        Log.d("DEATH", "LoadData: ${currentDeath.value}")


                    }

                    else {

                        showDialouge.value = 1

                    }

                   // JumpedPlatfrom.value -= 1
                    println(currentDeath.value)

                    if (currentDeath.value != 0) {
                        countDownTimer.cancel()
                        countDownTimer.start()
                    }

                    animState.value = false
                    isWrongState.value = false



                    return@Canvas
                }

                index.value = index.value - 1

                if (index.value < 0) {
                    animState.value = false
                    return@Canvas
                }

                p1 = listOfOffset[index.value]
                p2 = if (index.value != 0) {
                    listOfOffset[index.value - 1]
                } else listOfOffset[index.value]


                if (listOfOffset.first() != p1) {
                    isLastPlatfrom.value = false
                    x = p1.x
                    y = p1.y
                } else {
                    isLastPlatfrom.value = true
                }


                JumpedPlatfrom.value -= 1
                points.value += 10
                animState.value = !animState.value


                isSkipping.value = false

                if (currentDeath.value != 0) {
                    countDownTimer.cancel()
                    countDownTimer.start()
                }


                if (listOfOffset.first() == p1) {
                    animState.value = false
                    showDialouge.value = 2


                    countDownTimer.cancel()
                }


                //  invalidations++


            }


        }


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun QuestionCard(
        animationState: MutableState<Boolean>,
        isWrongState: MutableState<Boolean>,
        timeData: MutableState<Long>,
        currentDeath: MutableState<Int>,
        isSkippping: MutableState<Boolean>,
    ) {
        val context = LocalContext.current
        mMediaPlayer = MediaPlayer.create(context, R.raw.jump_s)
        val wrongPlayer = MediaPlayer.create(context, R.raw.fail)
        Card(

            modifier = Modifier.fillMaxWidth(),
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
                    "${
                        if (isSkippping.value) {
                            "Question will be skipped"
                        } else {
                            "This is a question of the game ?"
                        }
                    } ${(timeData.value / 1000).toInt()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                        .background(color = Color.White)
                        .padding(vertical = 8.dp),
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
                        "Right",
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1.0f)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                            .background(color = Color.Magenta)
                            .padding(vertical = 8.dp)
                            .clickable(
                                enabled =  !isLastPlatfrom.value
                            ){

                                if (!animationState.value && !isWrongState.value && !isLastPlatfrom.value && currentDeath.value != 0) {
                                    countDownTimer.cancel()
                                    mMediaPlayer.start()

                                    Handler(Looper.getMainLooper()).postDelayed(50) {

                                        animationState.value = !animationState.value

                                    }
                                }


                            },
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )


                    Text(
                        "Right",
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1.0f)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                            .background(color = Color.Magenta)
                            .padding(vertical = 8.dp)
                            .clickable(
                                enabled =  !isLastPlatfrom.value
                            ) {
                                if (!animationState.value && !isWrongState.value && !isLastPlatfrom.value && currentDeath.value != 0 ) {
                                    countDownTimer.cancel()
                                    mMediaPlayer.start()

                                    Handler(Looper.getMainLooper()).postDelayed(50) {

                                        animationState.value = !animationState.value

                                    }
                                }

                            },
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
                        "Wrong",
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1.0f)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                            .background(color = Color.Magenta)
                            .padding(vertical = 8.dp)
                            .clickable (
                                enabled = currentDeath.value != 0

                            )
                            {

                                if (!animationState.value && !isWrongState.value) {
                                    Log.d("WRONGCALL", "QuestionCard: call ")
                                    isWrongState.value = true
                                    countDownTimer.cancel()
                                    wrongPlayer.start()
                                    currentDeath.value = currentDeath.value - 1

                                }


                            },
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )


                    Text(
                        "Wrong",
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1.0f)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                            .background(color = Color.Magenta)
                            .padding(vertical = 8.dp)
                            .clickable(

                                enabled = currentDeath.value != 0


                            ) {

                                if (!animationState.value && !isWrongState.value) {
                                    Log.d("WRONGCALL", "QuestionCard: call ")
                                    countDownTimer.cancel()
                                    wrongPlayer.start()
                                    currentDeath.value = currentDeath.value - 1
                                    isWrongState.value = true
                                }
                                // Handle the click


                            },
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


    @Composable
    fun callTimer() {
        DisposableEffect(key1 = "key") {
            countDownTimer.cancel()
            countDownTimer.start()

            onDispose {
                countDownTimer.cancel()
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DefaultxPreview() {
    val animationState = remember { mutableStateOf(false) }
    val isWrongState = remember { mutableStateOf(false) }
    //QustionCard(animationState, isWrongState)
}


class ClickHelper private constructor() {
    private val now: Long
        get() = System.currentTimeMillis()
    private var lastEventTimeMs: Long = 0
    fun clickOnce(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }

    companion object {
        @Volatile
        private var instance: ClickHelper? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ClickHelper().also { instance = it }
            }
    }
}