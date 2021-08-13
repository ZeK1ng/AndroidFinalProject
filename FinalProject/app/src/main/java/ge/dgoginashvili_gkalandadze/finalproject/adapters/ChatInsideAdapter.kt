package ge.dgoginashvili_gkalandadze.finalproject.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.MessageContainer
import com.example.clientapp.recycler.ChatMessageLeftViewHolder
import com.example.clientapp.recycler.ChatMessageRightViewHolder
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.activities.ChatActivity

class ChatInsideAdapter (private val activity: ChatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var chat :Pair<String,MessageContainer> = Pair("",MessageContainer())

    override fun getItemViewType(position: Int): Int {
        if(chat.second.chat[position].from != activity.user?.email?.let { it.substring(0, it.indexOf('@')) }) {
            return 1
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 0) {
            return ChatMessageRightViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_chat_right, parent, false))
        } else {
            return ChatMessageLeftViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_chat_left, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return chat.second.chat.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == 0) {
            (holder as ChatMessageRightViewHolder).message.text = chat.second.chat[position].text
        } else {
            (holder as ChatMessageLeftViewHolder).message.text = chat.second.chat[position].text
        }
    }
    fun setData(c:Pair<String,MessageContainer>){
        chat = c
        notifyDataSetChanged()
    }

}
