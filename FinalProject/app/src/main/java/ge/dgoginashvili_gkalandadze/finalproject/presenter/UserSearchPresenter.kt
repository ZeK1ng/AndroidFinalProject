package ge.dgoginashvili_gkalandadze.finalproject.presenter

import ge.dgoginashvili_gkalandadze.finalproject.activities.UserSearchActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.MainInteractor
import ge.dgoginashvili_gkalandadze.finalproject.interactor.UserSearchInteractor

class UserSearchPresenter(val userSearchActivity: UserSearchActivity) {
    private val userSearchInteractor = UserSearchInteractor(this)
    fun loadUsers() {
        userSearchInteractor.loadUsers()
    }

    fun usersFetched(data: ArrayList<Pair<String, String>>) {
        userSearchActivity.usersFetched(data)
    }
}