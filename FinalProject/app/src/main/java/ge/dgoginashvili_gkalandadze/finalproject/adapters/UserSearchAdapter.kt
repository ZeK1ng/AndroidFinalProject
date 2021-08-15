package ge.dgoginashvili_gkalandadze.finalproject.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.MessageContainer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.activities.ChatActivity
import ge.dgoginashvili_gkalandadze.finalproject.activities.UserSearchActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class UserSearchAdapter(val UserSearchActivity: UserSearchActivity) :
    RecyclerView.Adapter<UserViewHolder>() {
    private var usersData: ArrayList<Pair<String, String>> = arrayListOf()
    private var fb = FirebaseAuth.getInstance()
    val userName = fb.currentUser?.email?.substring(0, fb.currentUser!!.email!!.indexOf('@'))
    var to = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_view_holder, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.nickname.text = usersData[position].first
        holder.status.text = usersData[position].second
        holder.itemView.setOnClickListener {
            var chatExists = false
            Firebase.database.getReference("Messages").get().addOnSuccessListener {
                val data = it.value as ArrayList<HashMap<String, String>>
                data.removeFirstOrNull()
                Log.d("data", data.toString())
                for (dt in data) {
                    if(dt != null){
                        if ((dt["talk1"] == userName && dt["talk2"] == usersData[position].first) || (dt["talk2"] == userName && dt["talk1"] == usersData[position].first)) {
                            chatExists = true
                            if(dt["talk1"] == userName){
                                to = dt["talk2"]!!
                            }else{
                                to = dt["talk1"]!!
                            }
                            stuff1()
                            break
                        }
                    }

                }
                if (!chatExists) {
                    stuff2(position)
                }
            }
        }
    }

    private fun stuff2(position: Int) {
        val dbase = Firebase.database.getReference("Messages")
        dbase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var res = 0
                for (child in snapshot.children) {
                    Log.d("childKey",child.key.toString())

                    val key = child.key!!.toInt()
                    if(child.key!!.toInt() > res){
                        res  = key
                    }
                }
                startChat(res,position)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
    private fun startChat(res: Int, position: Int) {
        val int = Intent(UserSearchActivity.applicationContext, ChatActivity::class.java)
        Log.d("Chat", "NEwwww")
        int.putExtra("newChat", true)
        val msgCont = MessageContainer()
        msgCont.chat = arrayListOf()
        msgCont.talk1 = usersData[position].first
        msgCont.talk2 = userName!!
        val bndl = bundleOf("msgCont" to msgCont)
        int.putExtras(bndl)
        val nextChatIndex = res+1
        int.putExtra("nextChatIndex",nextChatIndex.toString())
        UserSearchActivity.startActivity(int)
    }

    private fun stuff1() {
        fetchChats()

    }
    private fun fetchChats() {
        val firebaseAuth = FirebaseAuth.getInstance()
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
                        chatFetched(resArr.filterNotNull() as ArrayList<Pair<String, MessageContainer>>)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("err")
            }
        })
    }
    private fun chatFetched(chatsList: ArrayList<Pair<String, MessageContainer>>){
        for(data in chatsList){
            val msgCont = data.second
            if ((msgCont.talk1 == userName && msgCont.talk2 == to) ||(msgCont.talk1 == to && msgCont.talk2==userName)){
                val res = Pair(data.first,msgCont)
                val int = Intent(UserSearchActivity.applicationContext, ChatActivity::class.java)
                int.putExtra("newChat", false)
                val bndl = bundleOf("chat" to res)
                int.putExtras(bndl)
                UserSearchActivity.startActivity(int)
            }
        }
        Log.d("Chatts",chatsList.toString())
    }
    override fun getItemCount(): Int {
        return usersData.size
    }

    fun setData(data: ArrayList<Pair<String, String>>) {
        usersData = data
        notifyDataSetChanged()
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var avatar = itemView.findViewById<ImageView>(R.id.profileImage)
    var nickname = itemView.findViewById<TextView>(R.id.profileName)
    var status = itemView.findViewById<TextView>(R.id.profileStatus)

}
