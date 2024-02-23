package io.github.kamo.vrcm.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import io.github.kamo.vrcm.R
import io.github.kamo.vrcm.data.api.UserStatus
import io.github.kamo.vrcm.ui.theme.GameColor
import org.koin.compose.koinInject

@Composable
fun AuthFold(
    iconYOffset: Dp = (-180).dp,
    cardYOffset: Dp = 0.dp,
    cardAlpha: Float = 1.00f,
    cardHeightDp: Dp = 380.dp,
    shapeDp: Dp = 30.dp,
    cardContext: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .width(128.dp)
                .align(Alignment.Center)
                .offset(y = iconYOffset)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = cardYOffset)
                .alpha(cardAlpha)
                .height(cardHeightDp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(topStart = shapeDp, topEnd = shapeDp),
        ) {
            cardContext()
        }
    }
}

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun SnackBarToast(
    modifier: Modifier = Modifier,
    text: String,
    onEffect: () -> Unit,
    content: @Composable (SnackbarData) -> Unit = {
        Text(text = it.visuals.message)
    }
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(text) {
        if (text.isNotBlank()) {
            snackBarHostState.showSnackbar(text)
            onEffect()
        }
    }
    SnackbarHost(
        modifier = modifier,
        hostState = snackBarHostState
    ) {
        Snackbar(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(12.dp),
            actionOnNewLine = true
        ) {
            content(it)
        }
    }
}

@Composable
fun UserStateIcon(
    modifier: Modifier = Modifier,
    iconUrl: String?,
    userStatus: UserStatus
) {
    AImage(
        modifier = Modifier
            .sateCircle(GameColor.Status.fromValue(userStatus))
            .then(modifier)
            .aspectRatio(1f)
            .clip(CircleShape),
        imageData = iconUrl,
        contentDescription = "UserStateIcon"
    )
}

@Composable
fun AImage(
    modifier: Modifier = Modifier,
    imageData: Any?,
    color: Color = MaterialTheme.colorScheme.inverseOnSurface,
    contentDescription: String? = null,
    imageLoader: ImageLoader = koinInject(),
    contentScale: ContentScale = ContentScale.Crop,
) {
    val placeholder = remember(color) { ColorPainter(color) }
    AsyncImage(
        modifier = modifier,
        model = imageData,
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        placeholder = placeholder,
        error = placeholder,
        contentScale = contentScale,
    )
}


