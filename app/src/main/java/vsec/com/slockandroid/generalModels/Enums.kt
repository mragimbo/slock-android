package vsec.com.slockandroid.generalModels

enum class PasswordScore {
    WEAK, AVERAGE, STRONG, MARVELOUS
}

enum class ButtonState {
    EMAIL_VALID,
    EMAIL_EQUAL,
    FIRST_NAME_VALID,
    LAST_NAME_VALID,
    PASSWORD_VALID,
    PASSWORD_EQUAL,

    FIRST_REGISTER_BUTTON_OK,
    SECOND_REGISTER_BUTTON_OK,
    LOGIN_BUTTON_OK
}