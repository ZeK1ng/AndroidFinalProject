package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.presenter.LoginPresenter

class LoginActivity : AppCompatActivity() {
    private lateinit var nameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var signInBtn: AppCompatButton
    private lateinit var signUpBtn: AppCompatButton
    private lateinit var loginPresenter:LoginPresenter
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
        var pass = passwordText.text.trim()
        if(!checkValidInputs(name,pass)){
            return
        }
        checkCredentials(name.toString(),pass.toString())
    }

    private fun checkCredentials(name: String, pass: String) {
        loginPresenter.checkCredentials(name,pass)
    }
    fun onSuccessfulCredentials(){
        Log.d("USerAuth","Successfull Auth")
    }
    fun onFailedCredentials() {
        Log.d("USerAuth","Failed Auth")
    }
    private fun checkValidInputs(name: CharSequence, pass: CharSequence): Boolean {
        if (TextUtils.isEmpty(name)){
            nameText.error = "Please Enter name"
            return false
        }
        if (TextUtils.isEmpty(pass)){
            passwordText.error = "Please Enter Password"
            return false
        }
        if(pass.length <5){
            passwordText.error = "Password length must be at least 5 symbols"
            return false
        }
        return true
    }

    private fun hashPass(pass: CharSequence): CharSequence {
        //TODO
        return pass
    }

    //TODO load new SingUp Activity
    private fun loadSignUpActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


}