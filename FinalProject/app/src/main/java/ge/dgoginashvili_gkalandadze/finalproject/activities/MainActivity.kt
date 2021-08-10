package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.MessageContainer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.adapters.MainPageAdapter
import ge.dgoginashvili_gkalandadze.finalproject.presenter.MainPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    private lateinit var mainPresenter: MainPresenter
    private lateinit var recycler: RecyclerView
    private lateinit var bottomMenu: BottomNavigationView
    private val menuItemListener =
        NavigationBarView.OnItemSelectedListener { menuItem -> changeMenu(menuItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allchat)
        setupPresenter()
        bottomMenu = findViewById<BottomNavigationView>(R.id.bottom_menu)
        initMenuListener()
        recycler = findViewById(R.id.messages_recyclerview)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = MainPageAdapter(this)
        mainPresenter.loadChat()
    }

    private fun initMenuListener() {
        bottomMenu.setOnItemSelectedListener(menuItemListener)
    }

    private fun setupPresenter() {
        mainPresenter = MainPresenter(this)
    }

    fun updateChats(personArray: ArrayList<Pair<String, MessageContainer>>) {
        CoroutineScope(Dispatchers.Main).async {
            (recycler.adapter as MainPageAdapter).setHistory(personArray)
        }
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