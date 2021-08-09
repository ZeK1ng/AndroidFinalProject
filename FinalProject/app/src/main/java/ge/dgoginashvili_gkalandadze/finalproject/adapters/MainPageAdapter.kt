package ge.dgoginashvili_gkalandadze.finalproject.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.Message
import com.example.clientapp.models.MessageContainer
import com.example.clientapp.models.UserChat
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.activities.ChatActivity
import ge.dgoginashvili_gkalandadze.finalproject.presenter.MainPresenter
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter
import java.util.*

class MainPageAdapter(val conversationActivity: AppCompatActivity) : RecyclerView.Adapter<MainPageViewHolder>() {
    private var data: ArrayList<MessageContainer> = arrayListOf()
    private var search = false
    private lateinit var profilePresenter: ProfilePresenter

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): MainPageViewHolder {
        return MainPageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_conversation_viewholder, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        holder.nickname.text = data[position].talk1
    }

    fun setHistory(history: ArrayList<MessageContainer>) {
        data = history
        notifyDataSetChanged()
    }
}