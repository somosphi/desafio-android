package com.newton.phi.common

object extract {

    infix fun safe(value: String?): String = value ?: ""

}