package com.example.vynkapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.vynkapp.model.Post


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val posts by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feed") },
                navigationIcon = {
                    IconButton(onClick = {
                        authViewModel.logout()
                        navController.navigate("login") {
                            popUpTo("feed") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("upload")
                    }) {
                        Icon(Icons.Default.CloudUpload, contentDescription = "Upload")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(posts) { index, post ->
                        val isVisible = remember {
                            derivedStateOf { listState.firstVisibleItemIndex == index }
                        }
                        VideoPost(
                            post = post,
                            isVisible = isVisible.value
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VideoPost(post: Post, isVisible: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        VideoPlayer(
            videoUrl = post.videoUrl,
            isVisible = isVisible,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = post.caption,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun VideoPlayer(videoUrl: String, isVisible: Boolean, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            VideoView(context).apply {
                setVideoURI(Uri.parse(videoUrl))
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                    if (isVisible) mediaPlayer.start()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("VideoPlayer", "Error: what=$what, extra=$extra")
                    true
                }
            }
        },
        modifier = modifier,
        update = { videoView ->
            if (isVisible) {
                if (!videoView.isPlaying) videoView.start()
            } else {
                if (videoView.isPlaying) videoView.pause()
            }
        }
    )
}