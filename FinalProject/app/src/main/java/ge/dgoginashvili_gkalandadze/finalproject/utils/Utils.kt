package ge.dgoginashvili_gkalandadze.finalproject.utils
import android.view.View
import android.view.inputmethod.InputMethodManager
import org.mindrot.jbcrypt.BCrypt

class Utils {
    companion object {
        fun hashPwd(password:String):String{
            return BCrypt.hashpw(password,BCrypt.gensalt())
        }
        fun checkPassByHash(password: String,userPasswordHash:String):Boolean{
            return BCrypt.checkpw(password,userPasswordHash)
        }

    }

}