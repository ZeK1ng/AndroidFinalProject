package ge.dgoginashvili_gkalandadze.finalproject.activities

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.adapters.MainPageAdapter
import ge.dgoginashvili_gkalandadze.finalproject.adapters.UserSearchAdapter
import ge.dgoginashvili_gkalandadze.finalproject.presenter.UserSearchPresenter

class UserSearchActivity : AppCompatActivity() {
    private lateinit var searcher: SearchView
    private lateinit var userSearchPresenter:UserSearchPresenter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)
        initView()
        setupPresenter()
        setupRecView()
        loadUsers()
    }

    private fun loadUsers() {
        userSearchPresenter.loadUsers()
    }

    private fun setupRecView() {
        recycler = findViewById(R.id.userRecView)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = UserSearchAdapter(this)
    }

    private fun setupPresenter() {
        userSearchPresenter = UserSearchPresenter(this)
    }

    private fun initView() {
        searcher = findViewById(R.id.search)
        searcher.setOnClickListener{
            searcherClicked()
        }
    }

    private fun searcherClicked() {
        searcher.isIconified = false;
    }
}