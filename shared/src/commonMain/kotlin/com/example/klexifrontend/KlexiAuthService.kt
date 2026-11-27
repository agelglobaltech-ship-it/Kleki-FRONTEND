package com.example.klexifrontend.network

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class RegistroMenorRequest(
    val nombreMenor: String,
    val userMenor: String,
    val rangoId: Int,
    val nombreTutor: String,
    val userTutor: String,
    val correoTutor: String,
    val contrasena: String
)

@Serializable
data class RegistroAdultoRequest(
    val nombreCompleto: String,
    val nombreUsuario: String,
    val correoElectronico: String,
    val rangoId: Int,
    val contrasena: String
)

@Serializable
data class LoginRequest(
    val nombreUsuario: String,
    val contrasena: String
)

@Serializable
data class AuthResponse(
    val exito: Boolean,
    val mensaje: String,
    val usuarioId: Int? = null,
    val rolId: Int? = null,
    val nombreCompleto: String? = null
)

object KlexiAuthService {

    suspend fun login(usuario: String, contrasena: String): AuthResponse {
        return try {
            klexiHttpClient.post("${getBaseUrl()}/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(nombreUsuario = usuario, contrasena = contrasena))
            }.body()
        } catch (e: Exception) {
            AuthResponse(exito = false, mensaje = "Error de conexión: ${e.message}")
        }
    }

    suspend fun registrarMenorConTutor(datos: RegistroMenorRequest): AuthResponse {
        return try {
            klexiHttpClient.post("${getBaseUrl()}/api/auth/registro-menor") {
                contentType(ContentType.Application.Json)
                setBody(datos)
            }.body()
        } catch (e: Exception) {
            AuthResponse(exito = false, mensaje = "Error de conexión: ${e.message}")
        }
    }

    suspend fun registrarAdulto(datos: RegistroAdultoRequest): AuthResponse {
        return try {
            klexiHttpClient.post("${getBaseUrl()}/api/auth/registro-adulto") {
                contentType(ContentType.Application.Json)
                setBody(datos)
            }.body()
        } catch (e: Exception) {
            AuthResponse(exito = false, mensaje = "Error de conexión: ${e.message}")
        }
    }
}