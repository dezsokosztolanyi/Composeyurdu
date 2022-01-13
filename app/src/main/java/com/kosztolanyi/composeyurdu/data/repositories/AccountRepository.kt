package com.kosztolanyi.composeyurdu.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.kosztolanyi.composeyurdu.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AccountRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun isUserSignedIn() : User?{
         val user = auth.currentUser
        return if (user == null){
            null
        }else{
            firestore.collection("user")
                .document(user.uid)
                .get()
                .await()
                .toObject<User>()
        }
    }

    fun exitAccount(){
        auth.signOut()
    }
}