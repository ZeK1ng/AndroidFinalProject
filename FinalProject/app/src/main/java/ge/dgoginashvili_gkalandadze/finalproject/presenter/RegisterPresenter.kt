package ge.dgoginashvili_gkalandadze.finalproject.presenter

import ge.dgoginashvili_gkalandadze.finalproject.activities.RegisterActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.RegisterInteractor

class RegisterPresenter(var registerActivity: RegisterActivity?) {

    private val registerInteractor = RegisterInteractor(this)

    fun initiateSignUp(name: CharSequence, pass: CharSequence, workStatustxt: CharSequence) {
        registerInteractor.registerUser(name.toString(),pass.toString(),workStatustxt.toString())

    }
    fun onSuccessfulRegister() {
        registerActivity?.registerSuccess()
    }

    fun onFailedSave() {
        registerActivity?.saveFail()
    }

    fun detachView() {
        registerActivity = null
    }


    fun onAuthFailed() {
        registerActivity?.authFail()
    }
}