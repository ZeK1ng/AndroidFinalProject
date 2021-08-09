package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.presenter.LoginPresenter

class LoginActivity : AppCompatActivity() {
    private lateinit var nameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var signInBtn: AppCompatButton
    private lateinit var signUpBtn: AppCompatButton
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var firebaseAuth: FirebaseAuth

//    override fun onStart() {
//        super.onStart()
//        firebaseAuth = Firebase.auth
//        if(firebaseAuth.currentUser != null){
//            loadUserpageActivity()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter = LoginPresenter(this)
        setupComponents()
    }

    private fun setupComponents() {
        nameText = findViewById(R.id.nicknameText)
        passwordText = findViewById(R.id.passwordText)
        signInBtn = findViewById(R.id.signinButton)
        signUpBtn = findViewById(R.id.signupButton)
        setupListeners()
    }

    private fun setupListeners() {
        signUpBtn.setOnClickListener {
            loadSignUpActivity()
        }
        signInBtn.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val name = nameText.text.trim()
        val pass = passwordText.text.trim()
        if (!checkValidInputs(name, pass)) {
            return
        }
        checkCredentials(name.toString(), pass.toString())
    }

    private fun handleSignOut() {
//        AuthUI.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        loginPresenter.detachView()
        super.onDestroy()
    }

    private fun checkCredentials(name: String, pass: String) {
        loginPresenter.checkCredentials(name, pass)
    }

    fun onSuccessfulCredentials() {
//        loadUserpageActivity()
        loadMainActivity()
    }

    fun onFailedCredentials() {
        nameText.error = "Invalid username or password"
        passwordText.error = "Invalid username or password"
    }

    private fun checkValidInputs(name: CharSequence, pass: CharSequence): Boolean {
        if (TextUtils.isEmpty(name)) {
            nameText.error = "Please Enter name"
            return false
        }
        if (TextUtils.isEmpty(pass)) {
            passwordText.error = "Please Enter Password"
            return false
        }
        if (pass.length < 5) {
            passwordText.error = "Password length must be at least 5 symbols"
            return false
        }
        return true
    }

    private fun loadSignUpActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun loadUserpageActivity() {
        val intent = Intent(this, ProfilePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK xor Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun loadMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK xor Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}