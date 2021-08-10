package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.Message
import com.example.clientapp.models.MessageContainer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.adapters.ChatInsideAdapter
import ge.dgoginashvili_gkalandadze.finalproject.adapters.MainPageAdapter

class ChatActivity : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    lateinit var chat: Pair<String, MessageContainer>
    lateinit var recyclerView: RecyclerView
    val database = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_inside)
        if (savedInstanceState == null) {
            val bndl = intent.extras
            chat = bndl?.getSerializable("chat") as Pair<String, MessageContainer>
        }
        val userMail = user?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val to = if (chat.second.talk1 == userName) chat.second.talk2 else chat.second.talk1
        recyclerView = findViewById(R.id.chat_inside_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ChatInsideAdapter(this)
        findViewById<Button>(R.id.sendButton).setOnClickListener {
            database.child("Messages").child(chat.first).get().addOnSuccessListener {
                val currChat = it.getValue(MessageContainer::class.java)
                if (currChat != null) {
                    chat = Pair(chat.first, currChat)
                }
                val txt = findViewById<EditText>(R.id.messageText).text.toString()
                val msg = Message(userName ?: "", to, txt, "")
                val list = chat.second.chat.toMutableList()
                list.add(msg)
                val cont = MessageContainer(list, chat.second.talk1, chat.second.talk2)
                chat = Pair(chat.first, cont)
                val mapMsg = cont.toMap()
                val updates = hashMapOf<String, Any>(
                    "/Messages/${chat.first}" to mapMsg
                )
                (recyclerView.adapter as ChatInsideAdapter).notifyDataSetChanged()
                recyclerView.scrollToPosition(chat.second.chat.size - 1)
                database.updateChildren(updates)
            }.addOnFailureListener {
            }
        }
    }
}