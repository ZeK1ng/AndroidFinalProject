package ge.dgoginashvili_gkalandadze.finalproject.interactor

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.presenter.UserSearchPresenter

class UserSearchInteractor(val UserSearchPresenter:UserSearchPresenter) {
    //Aqedan ro wamovigeb datas unda wavigo activitimde rogor list<pair<string,string>> da maqedan mere recycleris funqcia gamovidzaxo.
    fun loadUsers() {
        val dbase = Firebase.database.getReference("Users")
        dbase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = arrayListOf<Pair<String,String>>()
                for (child in snapshot.children) {
                    val name = child.child("username").value.toString()
                    val status = child.child("status").value.toString()
                    data.add(Pair(name,status))
                }
                UserSearchPresenter.usersFetched(data)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}