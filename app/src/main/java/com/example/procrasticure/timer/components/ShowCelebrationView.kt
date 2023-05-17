package com.example.procrasticure.timer.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

@Composable
fun ShowCelebration() {

    AndroidView(

        modifier = Modifier
            .fillMaxWidth(),

        factory = { ctx ->
            KonfettiView(context = ctx).apply {
            }
        },

        update = {

            it.build()
                .addColors(
                    android.graphics.Color.YELLOW,
                    android.graphics.Color.GREEN,
                    android.graphics.Color.MAGENTA,
                    android.graphics.Color.CYAN,
                    android.graphics.Color.RED
                )
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12))
                .setPosition(-50f, it.getWidth() + 50f, -50f)
                .streamFor(300, 5000L)
        }
    )
}