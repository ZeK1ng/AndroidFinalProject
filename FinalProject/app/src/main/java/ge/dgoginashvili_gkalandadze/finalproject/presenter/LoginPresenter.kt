package ge.dgoginashvili_gkalandadze.finalproject.presenter

import ge.dgoginashvili_gkalandadze.finalproject.activities.LoginActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.LoginInteractor

class LoginPresenter(var loginActivity:LoginActivity?) {

    private val loginInteractor = LoginInteractor(this)

    fun checkCredentials(name: String, pass: String) {
        loginInteractor.checkCredsForUser(name,pass)
    }

    fun onSuccessfulCredentials() {
        loginActivity?.onSuccessfulCredentials()
    }

    fun onFailedCredentials() {
        loginActivity?.onFailedCredentials()
    }

    fun detachView() {
        loginActivity = null
    }
}
