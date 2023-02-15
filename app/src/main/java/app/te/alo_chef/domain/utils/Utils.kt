package app.te.alo_chef.domain.utils

fun String.isValidEmail() = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
).matches(this)

fun String.isValidEgyNumber() = Regex(
    "^(00201|201|\\+201|01|1)([0125])([0-9]{8})\$"
).matches(this)
