package ge.dgoginashvili_gkalandadze.finalproject.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseError
import com.google.firebase.ktx.Firebase
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.activities.LoginActivity
import ge.dgoginashvili_gkalandadze.finalproject.presenter.ProfilePresenter
import java.io.ByteArrayOutputStream
import java.io.IOException

class ProfilePageFragment : Fragment() {
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


    override fun onStart() {
        super.onStart()
        firebaseAuth = Firebase.auth
        if (firebaseAuth.currentUser == null) {
            Log.d("authedUser", firebaseAuth.currentUser.toString())
            goToLogin()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_page, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupPresenter()
        loadProfile()
    }

    private fun loadProfile() {
        profilePresenter.loadProfile()
    }

    private fun setupPresenter() {
        profilePresenter = ProfilePresenter(this)
    }

    private fun setupViews(view: View) {
        avatar = view.findViewById(R.id.avatar)!!
        nameView = view.findViewById(R.id.profPageName)!!
        statusView = view.findViewById(R.id.profPageStatus)!!
        logoutButton = view.findViewById(R.id.logoutBtn)!!
        updateButton = view.findViewById(R.id.updateButton)!!
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
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            avatarPath = data?.data!!
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, avatarPath)
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
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK xor Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun dbError(error: DatabaseError) {
        Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT)
            .show();
        Log.e("FirebaseDb Error", error.toString())
    }

    fun updateFailed() {
        Toast.makeText(context, "Error occured on update", Toast.LENGTH_SHORT)
            .show();
        Log.e("FirebaseDb Error", "Error on update")
        statusView.error = "Error occured.Please try again"
    }
}