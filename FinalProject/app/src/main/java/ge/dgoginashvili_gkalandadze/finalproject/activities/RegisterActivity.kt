package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var workStatus: EditText
    private lateinit var signUpBtn: AppCompatButton
    private lateinit var dbase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
        initiateSignUp(name,pass,workStatustxt)

    }

    private fun initiateSignUp(name: CharSequence, pass: CharSequence, workStatustxt: CharSequence) {
        val newUser = UserData(name.toString(),pass.toString(),workStatustxt.toString())
        dbase = Firebase.database.getReference("Users")
        dbase.child(name.toString()).setValue(newUser).addOnSuccessListener {
            Toast.makeText(getApplicationContext(), "Profile created successfully", Toast.LENGTH_SHORT)
                .show();
//            val intent = Intent(this, profilePageActivity::class.java)
//            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(getApplicationContext(), "Failed to create Profile", Toast.LENGTH_SHORT)
                .show();
        }
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
