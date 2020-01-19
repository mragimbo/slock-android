package vsec.com.slockandroid.Controllers

import kotlin.math.roundToInt

object PasswordEvaluator{
    private val lowercaseLetters: String = "abcdefghijklmnopqrstuvwxyzåäö"
    private val uppercaseLetters: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÅ"
    private val numbers: String = "0123456789"
    private val specialCharacters: String = "@#=+!£$%&? "
    private val maxPasswordSize: Int = 50
    private val minPasswordSize: Int = 8

    //under 13 is too low
    //between 13 and 21 is medium
    //between 21 and 34 is good
    //between 34 and 55 is marvelous

    fun gradePassword(password: String): Int{
        var score = 0.0

        if(password.length < minPasswordSize){ return password.length }

        if( password.matches( Regex(".*["+this.lowercaseLetters+"].*") ) ) { score += 2 }
        if( password.matches( Regex(".*["+this.uppercaseLetters+"].*") ) ){ score += 2 }
        if( password.matches( Regex(".*["+this.numbers+"].*") ) ){ score += 1 }
        if( password.matches( Regex(".*["+this.specialCharacters+"].*") ) ){ score += 3 }
        if( password.length < 16){score -= 3}

            if(password.length > minPasswordSize){
                for (i in password){score += 1}
            }
        return score.roundToInt()
    }
}