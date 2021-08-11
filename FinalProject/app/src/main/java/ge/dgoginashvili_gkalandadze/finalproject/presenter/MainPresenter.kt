package ge.dgoginashvili_gkalandadze.finalproject.presenter

import com.example.clientapp.models.MessageContainer
import ge.dgoginashvili_gkalandadze.finalproject.fragment.MainPageFragment
import ge.dgoginashvili_gkalandadze.finalproject.interactor.MainInteractor

class MainPresenter(var mainActivity: MainPageFragment) {

    private val mainInteractor = MainInteractor(this)

    fun loadChat() {
        mainInteractor.fetchChats()
    }

    fun chatFetched(msg: ArrayList<Pair<String, MessageContainer>>) {
        mainActivity.updateChats(msg)
    }

}