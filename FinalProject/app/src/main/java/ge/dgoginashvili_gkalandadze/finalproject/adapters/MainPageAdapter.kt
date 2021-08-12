package ge.dgoginashvili_gkalandadze.finalproject.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.MessageContainer
import com.google.firebase.auth.FirebaseAuth
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.activities.ChatActivity
import ge.dgoginashvili_gkalandadze.finalproject.fragment.MainPageFragment
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter
import java.util.*

class MainPageAdapter(val conversationActivity: MainPageFragment) : RecyclerView.Adapter<MainPageViewHolder>() {
    private var data: ArrayList<Pair<String, MessageContainer>> = arrayListOf()
    private var search = false
    private lateinit var profilePresenter: ProfilePresenter
    private var fb = FirebaseAuth.getInstance()
    val userName = fb.currentUser?.email?.substring(0, fb.currentUser!!.email!!.indexOf('@'))
    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): MainPageViewHolder {
        return MainPageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_conversation_viewholder, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        if(data[position].second.talk1 == userName){
            holder.nickname.text = data[position].second.talk2
        }else{
            holder.nickname.text = data[position].second.talk1
        }
        holder.itemView.setOnClickListener {
            val int = Intent(conversationActivity.context, ChatActivity::class.java)
            int.putExtra("newChat",false)
            val bndl = bundleOf("chat" to data[position])
            int.putExtras(bndl)
            conversationActivity.startActivity(int)
        }
    }

    fun setHistory(history: ArrayList<Pair<String, MessageContainer>>) {
        data = history
        notifyDataSetChanged()
    }
}