package ge.dgoginashvili_gkalandadze.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ge.dgoginashvili_gkalandadze.finalproject.activities.LoginActivity
import ge.dgoginashvili_gkalandadze.finalproject.activities.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initBottomMenu() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, MainFragment()).commit()
    }

}