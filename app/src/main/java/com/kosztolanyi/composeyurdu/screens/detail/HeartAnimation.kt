package com.kosztolanyi.composeyurdu.screens.detail

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kosztolanyi.composeyurdu.R

enum class Like{Initial, Liked, Gone}

@Composable
fun HeartAnimationDemo() {
    var likeState by remember{mutableStateOf(MutableTransitionState(Like.Gone))}

    val transition = updateTransition(transitionState = likeState, label = "Like Transition")

    val heartAlpha by transition.animateFloat(transitionSpec = {
        when{
            Like.Initial isTransitioningTo Like.Liked -> keyframes {
                durationMillis = 500
                0f at 0
                0.5f at 255
                1f at 400
            }

            Like.Liked isTransitioningTo  Like.Gone -> tween(300)
            else -> snap()
        }
    },
        label = "Alpha"
    ) {
        when(it){
            Like.Liked -> 1f
            else -> 0f
        }


    }


    val heartScale by transition.animateFloat(
        transitionSpec = {
            when{
                Like.Initial isTransitioningTo Like.Liked -> spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                Like.Liked isTransitioningTo Like.Gone -> tween(300)
                else -> snap()
            }
        },
        label = "Scale"
    ){
        when(it){
            Like.Liked -> 3f
            else -> 0f
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onDoubleTap = { likeState = MutableTransitionState(Like.Initial) })
        }
    ){
        if(likeState.currentState == Like.Initial) likeState.targetState = Like.Liked
        else if (likeState.currentState == Like.Liked) likeState.targetState = Like.Gone

        Image(painterResource(R.drawable.ic_launcher_foreground), contentDescription = "Apples",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Icon(
            Icons.Filled.Favorite, contentDescription = "Heart",
            tint = Color.White,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .graphicsLayer {
                    alpha = heartAlpha
                    scaleX = heartScale
                    scaleY = heartScale
                }
        )
    }
}