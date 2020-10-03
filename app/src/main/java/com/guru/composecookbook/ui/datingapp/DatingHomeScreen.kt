package com.guru.composecookbook.ui.datingapp

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.R
import com.guru.composecookbook.theme.purple
import com.guru.composecookbook.theme.typography
import com.guru.composecookbook.ui.Animations.FloatMultiStateAnimationCircleCanvas
import com.guru.composecookbook.ui.datingapp.components.DraggableCard
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import com.guru.composecookbook.ui.utils.verticalGradientBackground
import kotlin.random.Random

@Composable
fun DatingHomeScreen() {
    val configuration = ConfigurationAmbient.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight - 200.dp
    var reload = remember { mutableStateOf(false) }
    Surface(modifier = Modifier.fillMaxSize()) {
        var persons = mutableListOf<Album>()
        persons.addAll(SpotifyDataProvider.albums.take(15))
        reload.value = false
        val boxModifier = Modifier
        Box(
            modifier = boxModifier.verticalGradientBackground(
                listOf(
                    Color.White,
                    purple.copy(alpha = 0.2f)
                )
            )
        ) {
            DatingLoader(modifier = boxModifier)
            persons.forEachIndexed { index, album ->
                DraggableCard(
                    item = album,
                    modifier = Modifier.fillMaxWidth()
                        .preferredHeight(cardHeight)
                        .padding(
                            top = 16.dp + (index + 2).dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    { swipeResult, album ->
                        if (persons.isNotEmpty()) {
                            persons.remove(album)
                        }
                    }
                ) {
                    CardContent(album)
                }
            }
        }
    }
}

@Composable
fun CardContent(album: Album) {
    Column {
        Image(
            asset = imageResource(album.imageId),
            contentScale = ContentScale.Crop,
            modifier = Modifier.weight(1f)
        )
        Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(
                text = album.artist,
                style = typography.h6,
                modifier = Modifier.padding(end = 8.dp).weight(1f)
            )
            Icon(
                asset = Icons.Outlined.Place,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                tint = purple,
            )
            Text(
                text = "${Random.nextInt(1, 15)} km",
                style = typography.body2,
                color = purple
            )
        }
        Text(
            text = "Age: ${Random.nextInt(21, 30)}, Casually browsing..",
            style = typography.subtitle2,
            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
        )
        Text(
            text = "Miami, Florida",
            style = typography.subtitle2,
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}

@Composable
fun DatingLoader(modifier: Modifier) {
    Box(alignment = Alignment.Center, modifier = modifier.fillMaxSize().clip(CircleShape)) {
        FloatMultiStateAnimationCircleCanvas(purple, 400f)
        Image(
            asset = imageResource(id = R.drawable.adele21),
            modifier = modifier.preferredSize(50.dp).clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    }
}


enum class SwipeResult {
    ACCEPTED, REJECTED
}