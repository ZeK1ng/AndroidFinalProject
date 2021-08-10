package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseError
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter
import java.io.ByteArrayOutputStream
import java.io.IOException

class ProfilePageActivity : AppCompatActivity() {
    private var GALLERY_REQUEST_CODE = 23;
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var profilePresenter: ProfilePresenter
    lateinit var avatar: ImageView
    lateinit var avatarPath: Uri
    private var bitmap: Bitmap? = null
    lateinit var nameView: TextView
    lateinit var statusView: TextView
    private lateinit var logoutButton: AppCompatButton
    private lateinit var updateButton: AppCompatButton
    private val baos = ByteArrayOutputStream()
    private lateinit var bottomMenu: BottomNavigationView
    private val menuItemListener =
        NavigationBarView.OnItemSelectedListener { menuItem -> changeMenu(menuItem) }

    override fun onStart() {
        super.onStart()
        firebaseAuth = Firebase.auth
        if (firebaseAuth.currentUser == null) {
            Log.d("authedUser", firebaseAuth.currentUser.toString())
            goToLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        bottomMenu = findViewById<BottomNavigationView>(R.id.bottom_menu)
        initMenuListener()
        setupViews()
        setupPresenter()
        loadProfile()
    }

    private fun initMenuListener() {
        bottomMenu.setOnItemSelectedListener(menuItemListener)
    }

    private fun loadProfile() {
        profilePresenter.loadProfile()
    }

    private fun setupPresenter() {
        profilePresenter = ProfilePresenter(this)
    }

    private fun setupViews() {
        avatar = findViewById(R.id.avatar)
        nameView = findViewById(R.id.profPageName)
        statusView = findViewById(R.id.profPageStatus)
        logoutButton = findViewById(R.id.logoutBtn)
        updateButton = findViewById(R.id.updateButton)
        setupListeners()
    }

    private fun setupListeners() {
        avatar.setOnClickListener {
            changeAvatar()
        }
        logoutButton.setOnClickListener {
            logout()
        }
        updateButton.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val newStatus = statusView.text
//  ეს შეიძლება გვქონდეს ან არ გვქონდეს
//        if (newStatus.isEmpty()){
//            statusView.error = "Please enter nonempty string"
//        }
        profilePresenter.updateProfile(bitmap, newStatus)
    }

    private fun changeAvatar() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//        startActivity(gallery)
        startActivityForResult(gallery, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            avatarPath = data?.data!!
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, avatarPath)
                avatar.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
//            avatar.setImageURI(data?.data)
        }
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

    fun dbError(error: DatabaseError) {
        Toast.makeText(applicationContext, "Error occured", Toast.LENGTH_SHORT)
            .show();
        Log.e("FirebaseDb Error", error.toString())
    }

    fun updateFailed() {
        Toast.makeText(applicationContext, "Error occured on update", Toast.LENGTH_SHORT)
            .show();
        Log.e("FirebaseDb Error", "Error on update")
        statusView.error = "Error occured.Please try again"
    }

    private fun changeMenu(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.bottom_menu_home) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else if (menuItem.itemId == R.id.bottom_menu_profile) {
            val intent = Intent(this, ProfilePageActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}
