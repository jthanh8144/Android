package com.example.microcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.microcontroller.ui.theme.MicrocontrollerTheme
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MicrocontrollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopHeader()
                        Content()
                    }
                }
            }
        }
    }
}

fun Color.Companion.parse(colorString: String): Color =
    Color(color = android.graphics.Color.parseColor(colorString))

var temperature by mutableStateOf("0.00")

@Preview
@Composable
fun TopHeader() {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .background(Color.parse("#ead7f5"))
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Nhiệt độ",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = Color.parse("#180b1f")
                )

                Text(text = temperature + "°C ",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = Color.parse("#180b1f"),
                    modifier = Modifier.padding(3.dp))
            }
        }
    }
}

@Preview
@Composable
fun Content() {
    var radius by remember {
        mutableStateOf(0f)
    }

    var shapeCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var handleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var angle by remember {
        mutableStateOf(0.0)
    }

    var percent by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
    ) {
        Text(
            text = "$percent%",
            fontSize = 50.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        handleCenter += dragAmount

                        angle = getRotationAngle(handleCenter, shapeCenter)
                        percent = (angle / 360.0 * 100).toInt()
                        change.consumeAllChanges()
                    }
                }
                .padding(30.dp)

        ) {
            shapeCenter = center

            radius = size.minDimension / 2

            val x = (shapeCenter.x + cos(Math.toRadians(angle)) * radius).toFloat()
            val y = (shapeCenter.y + sin(Math.toRadians(angle)) * radius).toFloat()

            handleCenter = Offset(x, y)

            drawCircle(color = Color.White.copy(alpha = 0.20f), style = Stroke(20f), radius = radius)
            drawArc(
                color = Color.parse("#685881"),
                startAngle = 0.0f,
                sweepAngle = angle.toFloat(),
                useCenter = false,
                style = Stroke(20f)
            )
            drawCircle(color = Color.parse("#c299fc"), center = handleCenter, radius = 40f)
        }
    }
}

private fun getRotationAngle(currentPosition: Offset, center: Offset): Double {
    val (dx, dy) = currentPosition - center
    val theta = atan2(dy, dx).toDouble()

    var angle = Math.toDegrees(theta)

    if (angle < 0) {
        angle += 360.0
    }
    return angle
}
