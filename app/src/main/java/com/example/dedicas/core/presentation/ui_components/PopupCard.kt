package com.example.dedicas.core.presentation.ui_components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupCard(
    alpha: Float,
    label: String,
    isLoading: Boolean = false,
    onImagePick: (uri: Uri) -> Unit,
    onBackClick: () -> Unit,
    onSendClick: () -> Unit
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            if (it != null) {
                imageUri = it
                onImagePick(it)
            }
        }

    Card(
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier
            .padding(25.dp)
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.65f)
            .alpha(alpha),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            ScreenHeader(title = label) {
                onBackClick()
            }
            if (isLoading) {
                CircularProgressIndicator(color = Orange)
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .fillMaxHeight(0.5f),
                    shape = RoundedCornerShape(7.dp),
                    elevation = CardDefaults.cardElevation(5.dp),
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.upload_image),
                                contentDescription = null,
                                tint = Orange,
                                modifier = Modifier.size(dimensionResource(R.dimen.upload_icon_size))
                            )
                        }
                    }
                }
                CustomButton(label = stringResource(R.string.send)) {
                    onSendClick()
                }
            }
        }
    }
}