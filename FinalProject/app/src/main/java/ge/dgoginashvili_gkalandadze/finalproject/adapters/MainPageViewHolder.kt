package ge.dgoginashvili_gkalandadze.finalproject.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import ge.dgoginashvili_gkalandadze.finalproject.R

class MainPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var avatar: ImageView
    var nickname: TextView
    var time: TextView
    var message: TextView

    init {
        avatar = itemView.findViewById(R.id.icon)
        nickname = itemView.findViewById(R.id.name)
        time = itemView.findViewById(R.id.time)
        message = itemView.findViewById(R.id.last_message)
    }

}