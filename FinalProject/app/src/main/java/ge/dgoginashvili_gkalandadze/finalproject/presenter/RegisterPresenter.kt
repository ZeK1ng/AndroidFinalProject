package ge.dgoginashvili_gkalandadze.finalproject.presenter

import ge.dgoginashvili_gkalandadze.finalproject.activities.RegisterActivity
import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import ge.dgoginashvili_gkalandadze.finalproject.interactor.RegisterInteractor
import ge.dgoginashvili_gkalandadze.finalproject.utils.Utils

class RegisterPresenter(var registerActivity: RegisterActivity?) {
    private val regInteractor = RegisterInteractor(this)

    fun initiateSignUp(name: CharSequence, pass: CharSequence, workStatustxt: CharSequence) {
        regInteractor.checkUserInDb(name.toString(),pass.toString(),workStatustxt.toString())

    }
    fun registerUser(name:String,pass:String,workStatustxt:String){
        val hashedPass = Utils.hashPwd(pass)
        val newUser = UserData(name, hashedPass, workStatustxt)
        regInteractor.saveUserToDB(newUser)
    }
    fun userAlreadyRegistered(){
        registerActivity?.showUserAlreadyRegisteredError()
    }
    fun onSuccessfulRegister(newUser: UserData) {
        registerActivity?.registerSuccess(newUser)
    }

    fun onFailedRegister() {
        registerActivity?.logFail()
    }

    fun detachView() {
        registerActivity = null
    }

    fun authUser(newUser: UserData) {
        regInteractor.authUser(newUser.name,newUser.pass)
    }
    fun authSuccess(){
        registerActivity?.authSuccess()
    }

    fun autFailed() {
        registerActivity?.authFailed()
    }
}