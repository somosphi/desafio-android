package com.newton.phi.common

interface Payload <T> {
    fun toModel(): T
}