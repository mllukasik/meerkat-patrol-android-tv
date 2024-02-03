@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.rookiebyte.meerkatpatrol

import android.content.Context
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.Text

private const val LOG_TAG = "CAMERA_VIEW"

@Composable
fun CameraView(navController: NavHostController) {
    val context = LocalContext.current
    val url = AppPreferences(context).getUrl()
    val error = if (url?.isNotEmpty() == true) null else "Url was not set"
    val lifecycleOwner = LocalLifecycleOwner.current
    val player = if (url != null) buildPlayer(context, url) else null

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (buttonRef, errorRef, playerRef) = createRefs()

        if (error == null) {
            VideoPlayer(
                exoPlayer = player,
                modifier = Modifier.constrainAs(playerRef) {
                    centerTo(parent)
                },
            )
        }

        if (error != null) {
            Text(
                error,
                Modifier.constrainAs(errorRef) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
            )
        }

        Button(
            onClick = {
                navController.navigate(NavigationItem.Settings.route)
            },
            colors = ButtonDefaults.colors(
                containerColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(9.dp),
            modifier = Modifier.constrainAs(buttonRef) {
                bottom.linkTo(parent.bottom, margin = 11.dp)
                start.linkTo(parent.start, margin = 11.dp)
            }
        ) {
            Icon(imageVector = Icons.Filled.Settings, "settings")
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                Log.d(LOG_TAG, "ON EVENT: " + event.name)
                player?.prepare()
            }
            if (event == Lifecycle.Event.ON_PAUSE) {
                Log.d(LOG_TAG, "ON EVENT: " + event.name)
                player?.release()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

private fun buildPlayer(context: Context, url: String): ExoPlayer {
    val exoPlayer = ExoPlayer.Builder(context).build()
    exoPlayer.setMediaItem(MediaItem.fromUri(url))
    exoPlayer.playWhenReady = true
    exoPlayer.volume = 0f
    return exoPlayer
}

@Composable
fun VideoPlayer(exoPlayer: ExoPlayer?, modifier: Modifier) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            PlayerView(context).apply {
                useController = false
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        },
        modifier = modifier,
    )
}
