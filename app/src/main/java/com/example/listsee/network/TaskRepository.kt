package com.example.listsee.network

import androidx.lifecycle.get
import com.example.listsee.core.ResultWrapper
import com.example.listsee.core.safeCall
import com.example.listsee.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    private val taskCollection = firestore.collection("Tasks")

    suspend fun createTask(task: Task): ResultWrapper<Void> = safeCall {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun getTask(id: String): ResultWrapper<Task> = safeCall {
        val snapShot = taskCollection.document(id).get().await()
        snapShot.toObject(Task::class.java) ?: throw Exception("Tarea no encontrada")
    }

    suspend fun getAllTasks(): ResultWrapper<List<Task>> = safeCall {
        val userId = firebaseAuth.currentUser?.uid ?: throw Exception("Usuario no autenticado")

        val querySnapshot = taskCollection.whereEqualTo("userId", userId).get().await()

        querySnapshot.documents.mapNotNull { document ->
            document.toObject(Task::class.java)
        }
    }

    suspend fun updateTask(task: Task): ResultWrapper<Void> = safeCall {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(id: String): ResultWrapper<Void> = safeCall {
        taskCollection.document(id).delete().await()
    }
}