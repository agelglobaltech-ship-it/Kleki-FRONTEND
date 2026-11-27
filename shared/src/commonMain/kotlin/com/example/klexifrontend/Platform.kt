package com.example.klexifrontend

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform