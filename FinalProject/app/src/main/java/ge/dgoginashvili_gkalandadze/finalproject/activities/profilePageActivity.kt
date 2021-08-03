package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.presenter.LoginPresenter
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter

class ProfilePageActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var profilePresenter: ProfilePresenter
    lateinit var nameView:TextView
    lateinit var statusView: TextView
    private lateinit var logoutButton: AppCompatButton
    private lateinit var updateButton: AppCompatButton
    override fun onStart() {
        super.onStart()
        firebaseAuth = Firebase.auth
        if(firebaseAuth.currentUser == null){
            Log.d("authedUser",firebaseAuth.currentUser.toString())
            goToLogin()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        setupViews()
        setupPresenter()
        loadProfile()
    }

    private fun loadProfile() {
        profilePresenter.loadProfile()
    }

    private fun setupPresenter() {
        profilePresenter = ProfilePresenter(this)
    }

    private fun setupViews() {
        nameView = findViewById(R.id.profPageName)
        statusView = findViewById(R.id.profPageStatus)
        logoutButton = findViewById(R.id.logoutBtn)
        updateButton = findViewById(R.id.updateButton)
        setupListeners()
    }

    private fun setupListeners() {
        logoutButton.setOnClickListener{
            logout()
        }
        updateButton.setOnClickListener{
            updateProfile()
        }
    }

    private fun updateProfile() {
        return
    }
    

    private fun logout() {
        firebaseAuth.signOut()
        goToLogin()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK xor Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
