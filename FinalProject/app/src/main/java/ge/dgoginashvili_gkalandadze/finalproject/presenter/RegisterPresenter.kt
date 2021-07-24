package ge.dgoginashvili_gkalandadze.finalproject.presenter

import ge.dgoginashvili_gkalandadze.finalproject.activities.RegisterActivity
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.interactor.RegisterInteractor
import ge.dgoginashvili_gkalandadze.finalproject.utils.Utils

class RegisterPresenter(var view: RegisterActivity) {
    private val regInteractor = RegisterInteractor(this)

    fun initiateSignUp(name: CharSequence, pass: CharSequence, workStatustxt: CharSequence) {
        val hashedPass = Utils.hashPwd(pass.toString())
        val newUser = UserData(name.toString(), hashedPass, workStatustxt.toString())
        regInteractor.saveUserToDB(newUser)
    }

    fun onSuccessfulRegister() {
        view.logSuccess()
    }

    fun onFailedRegister() {
        view.logFail()
    }
}