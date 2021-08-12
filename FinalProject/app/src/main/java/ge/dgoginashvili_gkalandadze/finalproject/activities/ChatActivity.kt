package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.Message
import com.example.clientapp.models.MessageContainer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.adapters.ChatInsideAdapter

class ChatActivity : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    lateinit var chat: Pair<String, MessageContainer>
    lateinit var recyclerView: RecyclerView
    val database = FirebaseDatabase.getInstance().reference
    private lateinit var chatUserName: AppCompatTextView
    private lateinit var chatUserStatus: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_inside)
        setupRecView()
        setupChat(savedInstanceState)


    }

    private fun setupChat(savedInstanceState: Bundle?) {
        chatUserName = findViewById(R.id.chat_user_name)
        chatUserStatus = findViewById(R.id.chat_user_status)
        val newChat = getChatType(savedInstanceState)
        val userMail = user?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        val to = if (chat.second.talk1 == userName) chat.second.talk2 else chat.second.talk1
        chatUserName.text = to
        val dbase = Firebase.database.getReference("Users")
        dbase.child(to).get().addOnSuccessListener { snapshot ->
            var userval = snapshot.value.toString()
            userval = userval.substring(1,userval.length-1)
            val map = userval.split(",").associate {
                val (left, right) = it.split("=")
                left to right
            }
            chatUserStatus.text = map["status"]
        }

        if (!newChat) {
            handleExistedChat(userName, to)
        } else {
            handleNewChat()
        }
    }

    private fun setupRecView() {
        recyclerView = findViewById(R.id.chat_inside_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ChatInsideAdapter(this)
    }

    private fun getChatType(savedInstanceState: Bundle?): Boolean {
        if (savedInstanceState == null) {
            val bndl = intent.extras
            if (bndl?.getBoolean("newChat") == false) {
                chat = bndl.getSerializable("chat") as Pair<String, MessageContainer>
                recyclerView.scrollToPosition(chat.second.chat.size - 1)
            } else {
                return true
            }
        }
        return false
    }

    private fun handleNewChat() {
        return
    }

    private fun handleExistedChat(userName: String?, to: String) {
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