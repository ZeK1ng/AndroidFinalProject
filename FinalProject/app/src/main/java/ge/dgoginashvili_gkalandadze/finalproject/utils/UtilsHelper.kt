package ge.dgoginashvili_gkalandadze.finalproject.utils

import org.mindrot.jbcrypt.BCrypt

class UtilsHelper {
    companion object {
        fun hashPwd(password: String): String {
//            return BCrypt.hashpw(password, BCrypt.gensalt())
            return password
        }

        fun checkPassByHash(password: String, userPasswordHash: String): Boolean {
            return BCrypt.checkpw(password, userPasswordHash)
        }
    }

}