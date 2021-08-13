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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.activities.ChatActivity
import ge.dgoginashvili_gkalandadze.finalproject.activities.UserSearchActivity
import java.util.ArrayList

class UserSearchAdapter(val UserSearchActivity: UserSearchActivity) :
    RecyclerView.Adapter<UserViewHolder>() {
    private var usersData: ArrayList<Pair<String, String>> = arrayListOf()
    private var fb = FirebaseAuth.getInstance()
    val userName = fb.currentUser?.email?.substring(0, fb.currentUser!!.email!!.indexOf('@'))
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
                    if ((dt["talk1"] == userName && dt["talk2"] == usersData[position].first) || (dt["talk2"] == userName && dt["talk1"] == usersData[position].first)) {
                        chatExists = true
                        stuff1()
                    }
                }
                if (!chatExists) {
                    stuff2(position)
                }
            }
        }
    }

    private fun stuff2(position: Int) {
        val int = Intent(UserSearchActivity.applicationContext, ChatActivity::class.java)
        Log.d("Chat", "NEwwww")
        int.putExtra("newChat", true)
        val msgCont = MessageContainer()
        msgCont.chat = arrayListOf()
        msgCont.talk1 = usersData[position].first
        msgCont.talk2 = userName!!
        val bndl = bundleOf("msgCont" to msgCont)
        int.putExtras(bndl)


        UserSearchActivity.startActivity(int)
    }

    private fun stuff1() {
        Log.d("Chat", "Existss")

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
