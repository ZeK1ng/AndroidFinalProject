package ge.dgoginashvili_gkalandadze.finalproject.presenter

import ge.dgoginashvili_gkalandadze.finalproject.activities.RegisterActivity
import ge.dgoginashvili_gkalandadze.finalproject.interactor.RegisterInteractor

class RegisterPresenter(var registerActivity: RegisterActivity?) {
    private val regInteractor = RegisterInteractor(this)

    fun initiateSignUp(name: CharSequence, pass: CharSequence, workStatustxt: CharSequence) {
        regInteractor.registerUser(name.toString(),pass.toString(),workStatustxt.toString())

    }
    fun onSuccessfulRegister() {
        registerActivity?.registerSuccess()
    }

    fun onFailedRegister() {
        registerActivity?.logFail()
    }

    fun detachView() {
        registerActivity = null
    }


    fun registerFailed() {
        registerActivity?.registerFailed()
    }
}