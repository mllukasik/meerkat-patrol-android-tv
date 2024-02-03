@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.rookiebyte.meerkatpatrol

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.tv.material3.Text

@Composable
fun SettingsView(navController: NavHostController) {
    val context = LocalContext.current
    var newUrl by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (buttonBackRef, buttonOkRef, textRef) = createRefs()

        TextField(value = newUrl,
            singleLine = true,
            onValueChange = { newUrl = it },
            modifier = Modifier.fillMaxWidth().padding(10.dp).constrainAs(textRef) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            }
        )

        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.colors(
                containerColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.constrainAs(buttonBackRef) {
                bottom.linkTo(parent.bottom, margin = 12.dp)
                start.linkTo(parent.start, margin = 12.dp)
            }
        ) {
            Icon(imageVector = Icons.Filled.KeyboardArrowLeft, "settings")
        }

        Button(
            onClick = {
                if (newUrl.isNotEmpty()) {
                    AppPreferences(context).setUrl(newUrl)
                }
                navController.popBackStack()
            },
            colors = ButtonDefaults.colors(
                containerColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier.constrainAs(buttonOkRef) {
                bottom.linkTo(parent.bottom, margin = 12.dp)
                centerHorizontallyTo(parent)
            }
        ) {
            Text("OK")
        }
    }
}
