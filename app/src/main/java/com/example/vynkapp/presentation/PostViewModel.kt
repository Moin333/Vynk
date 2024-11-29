package com.example.vynkapp.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vynkapp.data.PostRepository
import com.example.vynkapp.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.fetchPosts().collect { fetchedPosts ->
                    _posts.value = fetchedPosts
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadVideo(uri: Uri, caption: String, onUploadComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.uploadVideo(uri, caption)
            _isLoading.value = false
            onUploadComplete(result != null)
        }
    }
}
