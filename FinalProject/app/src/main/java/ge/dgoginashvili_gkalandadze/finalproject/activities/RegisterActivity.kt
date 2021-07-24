package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.presenter.RegisterPresenter

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var workStatus: EditText
    private lateinit var signUpBtn: AppCompatButton
    private lateinit var regPresenter:RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        regPresenter = RegisterPresenter(this)
        setupComponents()
    }

    private fun setupComponents() {
        nameText = findViewById(R.id.nicknameText)
        passwordText = findViewById(R.id.passwordText)
        signUpBtn = findViewById(R.id.signUpBtn)
        workStatus = findViewById(R.id.workStatusText)
        signUpBtn.setOnClickListener {
            handleSignUp()
        }
    }

    private fun handleSignUp() {
        val name = nameText.text.trim()
        var pass = passwordText.text.trim()
        val workStatustxt = workStatus.text.trim()
        if (!checkValidInputs(name, pass, workStatustxt)) {
            Toast.makeText(getApplicationContext(), "Please enter valid values", Toast.LENGTH_SHORT)
                .show();
            return
        }
        regPresenter.initiateSignUp(name,pass,workStatustxt)

    }

    fun logSuccess(){
        Log.d("SuccessRegister","1")
    }
    fun logFail(){
        Log.d("FailedRegister","0")

    }

    private fun checkValidInputs(
        name: CharSequence,
        pass: CharSequence,
        workStatustxt: CharSequence
    ): Boolean {
        if (TextUtils.isEmpty(name)) {
            nameText.error = "Please Enter name"
            return false
        }
        if (TextUtils.isEmpty(pass)) {
            passwordText.error = "Please Enter Password"
            return false
        }
        if (TextUtils.isEmpty(workStatustxt)) {
            workStatus.error = "Please Enter your status"
            return false
        }
        if(pass.length <=5 ){
            passwordText.error = "Password lenght must be 5 or more"
            return false
        }
        return true
    }

    private fun setupListeners() {
        return
    }
}
