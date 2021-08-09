package ge.dgoginashvili_gkalandadze.finalproject.presenter

import com.example.clientapp.models.MessageContainer
import ge.dgoginashvili_gkalandadze.finalproject.activities.MainActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.MainInteractor

class MainPresenter(var mainActivity: MainActivity?) {

    private val mainInteractor = MainInteractor(this)

    fun loadChat() {
        mainInteractor?.fetchChats()
    }

    fun chatFetched(msg: ArrayList<MessageContainer>) {
        mainActivity!!.updateChats(msg)
    }

}