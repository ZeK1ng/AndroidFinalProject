package ge.dgoginashvili_gkalandadze.finalproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.models.MessageContainer
import ge.dgoginashvili_gkalandadze.finalproject.R
import ge.dgoginashvili_gkalandadze.finalproject.adapters.MainPageAdapter
import ge.dgoginashvili_gkalandadze.finalproject.presenter.MainPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class MainPageFragment : Fragment() {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var recycler: RecyclerView
    private lateinit var searcher:SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearcher(view)
        setupRecycler(view)
        setupPresenter()
    }

    private fun setupSearcher(view: View) {
        searcher = view.findViewById(R.id.search)
        searcher.setOnClickListener{
            searcher.isIconified = false;
        }
    }

    private fun setupRecycler(view: View) {
        recycler = view.findViewById(R.id.messages_recyclerview)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = MainPageAdapter(this)
    }

    private fun setupPresenter() {
        mainPresenter = MainPresenter(this)
        mainPresenter.loadChat()
    }

    fun updateChats(personArray: ArrayList<Pair<String, MessageContainer>>) {
        CoroutineScope(Dispatchers.Main).async {
            (recycler.adapter as MainPageAdapter).setHistory(personArray)
        }
    }

}