package com.brizidiolauro.rfidapp


val authorized = arrayListOf<String>("UEA10", "UEA11", "UEA12", "UEA13", "UEA14")

fun isCodeAuthorized(code: String?): Boolean {
    return authorized.contains(code)
}

fun generatePlateByCode(code: String?): String {
    return ("EST" + code.hashCode())
}
