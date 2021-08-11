package ge.dgoginashvili_gkalandadze.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import ge.dgoginashvili_gkalandadze.finalproject.activities.UserSearchActivity
import ge.dgoginashvili_gkalandadze.finalproject.adapters.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var viewpager: ViewPager2
    private lateinit var bottomMenu: BottomNavigationView
    private lateinit var addButton: FloatingActionButton
    private val menuItemListener =
        NavigationBarView.OnItemSelectedListener { menuItem -> changeMenu(menuItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        initMenuListener()
    }

    private fun setupView() {
        viewpager = findViewById(R.id.viewpagerMain)
        viewpager.adapter = ViewPagerAdapter(this)
        bottomMenu = findViewById(R.id.bottom_menu)
        bottomMenu.background = null
        addButton = findViewById(R.id.add_person_fab)
        addButton.setOnClickListener{
            addButtonClicked()
        }
    }

    private fun addButtonClicked() {
        val intent = Intent(this, UserSearchActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK xor Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun initMenuListener() {
        bottomMenu.setOnItemSelectedListener(menuItemListener)
    }


    private fun changeMenu(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.bottom_menu_home) {
            viewpager.currentItem = viewpager.currentItem - 1
        } else if (menuItem.itemId == R.id.bottom_menu_profile) {
            viewpager.currentItem = viewpager.currentItem + 1
        }
        return true
    }


}