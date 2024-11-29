package com.example.vynkapp.presentation

import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(viewModel: PostViewModel = hiltViewModel()) {
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var caption by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf<Pair<String, Boolean>?>(null) }

    // Snackbar Host State
    val snackbarHostState = remember { SnackbarHostState() }

    // File Picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            videoUri = uri
        }
    )

    // Scaffold for Snackbar
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
            TextField(
                value = caption,
                onValueChange = { caption = it },
                label = { Text("Caption") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    // Open file picker to select a video
                    launcher.launch(arrayOf("video/*"))
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Select Video")
            }
            Button(
                onClick = {
                    videoUri?.let {
                        isUploading = true
                        viewModel.uploadVideo(it, caption) { success ->
                            isUploading = false
                            showMessage = if (success) {
                                "Video uploaded successfully!" to true
                            } else {
                                "Failed to upload video." to false
                            }
                        }
                    }
                },
                enabled = videoUri != null && !isUploading
            ) {
                Text(if (isUploading) "Uploading..." else "Upload")
            }
        }
    }

    // Show Snackbar when a message is available
    showMessage?.let { (message, isSuccess) ->
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK",
                duration = SnackbarDuration.Short
            )
            // Reset the message after showing
            showMessage = null
        }
    }
}
