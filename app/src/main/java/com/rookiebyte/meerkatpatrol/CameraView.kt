@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.rookiebyte.meerkatpatrol

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun CameraView(navController: NavHostController) {
    val context = LocalContext.current
    val url = AppPreferences(context).getUrl()
    val error = if (url?.isNotEmpty() == true) null else "Url was not set"

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (buttonRef, errorRef, progressIndicatorRef) = createRefs()

        Button(
            onClick = {
                      navController.navigate(NavigationItem.Settings.route)
            },
            colors = ButtonDefaults.colors(
                containerColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.constrainAs(buttonRef) {
                bottom.linkTo(parent.bottom, margin = 12.dp)
                start.linkTo(parent.start, margin = 12.dp)
            }
        ) {
            Icon(imageVector = Icons.Filled.Settings, "settings")
        }

        if (error?.isNotEmpty() != true) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(progressIndicatorRef) {
                        centerTo(parent)
                    }
                    .height(25.dp)
                    .padding(horizontal = 25.dp)
            )
        }

        if (error?.isNotEmpty() == true) {
            Text(
                error,
                Modifier.constrainAs(errorRef) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
            )
        }
    }
}
