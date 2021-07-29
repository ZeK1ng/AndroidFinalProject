package ge.dgoginashvili_gkalandadze.finalproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R

class profilePageActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        firebaseAuth = Firebase.auth
        if(firebaseAuth.currentUser != null){
            Log.d("authedUser",firebaseAuth.currentUser.toString())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
    }
}