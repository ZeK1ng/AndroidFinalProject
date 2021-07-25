package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.presenter.RegisterPresenter
import ge.dgoginashvili_gkalandadze.finalproject.utils.Utils

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var workStatus: EditText
    private lateinit var signUpBtn: AppCompatButton
    private lateinit var regPresenter: RegisterPresenter

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
            hideKeyboard()
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
        regPresenter.initiateSignUp(name, pass, workStatustxt)

    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
              if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        };

    }
    override fun onDestroy() {
        regPresenter.detachView()
        super.onDestroy()
    }
    fun logSuccess() {
        Log.d("SuccessRegister", "1")
        //TOdo GO To USerPage
    }

    fun logFail() {
        Log.d("FailedRegister", "0")

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
        if (pass.length < 5) {
            passwordText.error = "Password lenght must be 5 or more"
            return false
        }
        return true
    }

    fun showUserAlreadyRegisteredError() {
        nameText.error = "User already Registered with this name. Please try another one"
        Log.d("FailedRegister", "0")
    }

}
