package com.example.vynkapp.data

import android.net.Uri
import com.example.vynkapp.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    fun fetchPosts(): Flow<List<Post>> = flow {
        val snapshot = firestore.collection("posts").get().await()
        val posts = snapshot.documents.mapNotNull { it.toObject(Post::class.java) }
        emit(posts)
    }

    suspend fun uploadVideo(videoUri: Uri, caption: String): String? {
        val ref = storage.reference.child("videos/${videoUri.lastPathSegment}")
        return try {
            ref.putFile(videoUri).await() // Upload the video
            val downloadUrl = ref.downloadUrl.await()
            firestore.collection("posts").add(
                mapOf(
                    "videoUrl" to downloadUrl.toString(),
                    "caption" to caption,
                    "timestamp" to System.currentTimeMillis()
                )
            ).await()
            downloadUrl.toString()
        } catch (e: Exception) {
            null // Return null in case of an error
        }
    }
}
