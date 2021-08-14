package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.Message
import com.example.clientapp.models.MessageContainer
import com.example.clientapp.models.UserChat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.adapters.ChatInsideAdapter

class ChatActivity : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    lateinit var recyclerView: RecyclerView
    val database = FirebaseDatabase.getInstance().reference
    private lateinit var chatUserName: AppCompatTextView
    private lateinit var chatUserStatus: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_inside)
        setupRecView()
        setupChat()


    }

    private fun setupChat() {
        chatUserName = findViewById(R.id.chat_user_name)
        chatUserStatus = findViewById(R.id.chat_user_status)
        val newChat = getChatType()
        val userMail = user?.email
        val userName = userMail?.substring(0, userMail.indexOf('@'))
        if (!newChat){
            handleExistedChat(userName)
        }else{
            handleNewChat(userName)
        }

    }

    private fun setupRecView() {
        recyclerView = findViewById(R.id.chat_inside_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ChatInsideAdapter(this)
    }

    private fun getChatType(): Boolean {
        val bndl = intent.extras
        return bndl?.getBoolean("newChat")!!
    }

    private fun handleNewChat(userName: String?) {
        val bndl = intent.extras
        val chat = bndl?.getSerializable("msgCont") as MessageContainer
        var to = ""
        to = if(chat.talk1 == userName){
            chat.talk2
        }else{
            chat.talk1
        }
        val nextChatIndex = bndl.getString("nextChatIndex")!!
        Log.d("ChattMsgIndex",nextChatIndex.toString())
        recyclerView.scrollToPosition(chat.chat.size - 1)
        (recyclerView.adapter as ChatInsideAdapter).setData(Pair("",chat))
        chatUserName.text = to
        setStatus(to)
        var wasButtonClicked = false
        findViewById<Button>(R.id.sendButton).setOnClickListener {
            if(!wasButtonClicked){
                val txt = findViewById<EditText>(R.id.messageText).text.toString()
                val msg = Message(userName ?: "", to, txt, "")
                val list = chat.chat.toMutableList()
                list.add(msg)
                val cont = MessageContainer(list, chat.talk1, chat.talk2)
                val chatWrapper = Pair(nextChatIndex,cont)
                val newBndl = bundleOf("chat" to chatWrapper)
                intent.putExtras(newBndl)
                (recyclerView.adapter as ChatInsideAdapter).setData(chatWrapper)
                val mapMsg = cont.toMap()
                database.child("Messages").child(nextChatIndex).setValue(mapMsg)
                var ftime=true
                val dbaseChats = Firebase.database.getReference("Chats")
                dbaseChats.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(ftime){
                            var hadUser = false
                            for (child in snapshot.children) {
                                if(child.key.toString() == userName){
                                    val childVal = child.value as ArrayList<String>
                                    val chatList = childVal.toMutableList()
                                    if (!chatList.contains(nextChatIndex)){
                                        chatList.add(nextChatIndex)
                                        dbaseChats.child(userName).setValue(chatList)
                                    }
                                    hadUser = true
                                    break
                                }
                            }
                            if(!hadUser){
                                val chatList = arrayListOf<String>()
                                chatList.add(nextChatIndex)
                                if (userName != null) {
                                    dbaseChats.child(userName).setValue(chatList)
                                }
                            }
                            ftime = false
                        }

                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }else{
                handleExistedChat(userName)
            }

            wasButtonClicked = true

        }

    }


    private fun handleExistedChat(userName: String?) {
        val bndl = intent.extras
        var chat = bndl?.getSerializable("chat") as Pair<String, MessageContainer>
        recyclerView.scrollToPosition(chat.second.chat.size - 1)
        (recyclerView.adapter as ChatInsideAdapter).setData(chat)
        val to = if (chat.second.talk1 == userName) chat.second.talk2 else chat.second.talk1
        chatUserName.text = to
        setStatus(to)
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
                (recyclerView.adapter as ChatInsideAdapter).setData(chat)
                recyclerView.scrollToPosition(chat.second.chat.size - 1)
                database.updateChildren(updates)
            }.addOnFailureListener {
            }
        }
    }
    private fun setStatus(to: String) {
        val dbase = Firebase.database.getReference("Users")
        dbase.child(to).get().addOnSuccessListener { snapshot ->
            var userval = snapshot.value.toString()
            userval = userval.substring(1, userval.length - 1)
            val map = userval.split(",").associate {
                val (left, right) = it.split("=")
                left to right
            }
            chatUserStatus.text = map["status"]
        }
    }

}