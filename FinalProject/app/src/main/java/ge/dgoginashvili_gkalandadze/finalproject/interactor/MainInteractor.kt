package ge.dgoginashvili_gkalandadze.finalproject.interactor

import com.example.clientapp.models.MessageContainer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.presenter.MainPresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class MainInteractor(val mainPresenter: MainPresenter) {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun fetchChats() {
        val loggedUser = firebaseAuth.currentUser
        val userMail = loggedUser?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val dbase = Firebase.database.getReference("Chats")
        val dbaseM = Firebase.database.getReference("Messages")

        dbase.child(userName!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val arr = snapshot.value as ArrayList<String>
                    var resArr: Array<Pair<String, MessageContainer>?> = arrayOfNulls(arr.size)
                    val latch = CountDownLatch(arr.size)
                    GlobalScope.launch {
                        for ((ind, str) in arr.withIndex()) {
                            dbaseM.child(str).get().addOnSuccessListener {
                                val cont = it.getValue(MessageContainer::class.java)
                                if (cont != null) {
                                    resArr[ind] = Pair(str, cont)
                                }
                                latch.countDown()
                            }.addOnFailureListener {
                                latch.countDown()
                            }

                        }
                        latch.await(5, TimeUnit.SECONDS)
                        mainPresenter.chatFetched(resArr.filterNotNull() as ArrayList<Pair<String, MessageContainer>>)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("err")
            }
        })
    }
}