package com.example.klexifrontend.models

enum class UserRole(val displayName: String, val description: String) {
    ALUMNO("Usuario", "Alumno / Estudiante"),
    TUTOR("Tutor", "Padres / Tutor"),
    ADMIN("Administrativo", "Panel de Administración"),
    TERAPEUTA("Terapeuta", "Seguimiento Profesional")
}

enum class AgeRange(
    val label: String,
    val rangeText: String,
    val minAge: Int,
    val maxAge: Int,
    val requiresTutor: Boolean
) {
    PRIMERA_INFANCIA("Primera Infancia", "3 a 5 años", 3, 5, true),
    NINEZ_INTERMEDIA("Niñez Intermedia", "6 a 8 años", 6, 8, true),
    PREADOLESCENCIA("Preadolescencia", "9 a 12 años", 9, 12, true),
    ADOLESCENCIA("Adolescencia", "13 a 17 años", 13, 17, true),
    JUVENTUD("Juventud / Adultez Temprana", "18 a 25 años", 18, 25, false),
    ADULTEZ_MEDIA("Adultez Media", "26 a 44 años", 26, 44, false),
    ADULTEZ_MADURA("Adultez Madura", "45 a 60 años", 45, 60, false)
}

enum class AccessoryType {
    HAT, GLASSES, BOW, CROWN, COLOR
}

data class KlexiShopItem(
    val id: String,
    val name: String,
    val type: AccessoryType,
    val price: Int,
    val isUnlocked: Boolean = false,
    val isEquipped: Boolean = false
)

data class UserProfile(
    val id: String,
    val fullName: String,
    val email: String,
    val role: UserRole,
    val ageRange: AgeRange? = null,
    val tutorFullName: String? = null,
    val tutorEmail: String? = null,
    val points: Int = 120,
    val level: Int = 1,
    val isLocked: Boolean = false,
    val progressLectura: Float = 0.85f,
    val progressEscritura: Float = 0.70f,
    val progressComprension: Float = 0.75f
)

data class GameQuestion(
    val id: String,
    val prompt: String,
    val imageType: String, // e.g. "mushroom", "apple", "star", "cat"
    val correctAnswer: String,
    val options: List<String>
)

data class TherapistNote(
    val id: String,
    val studentName: String,
    val date: String,
    val text: String,
    val area: String
)

data class AdminUserSummary(
    val id: String,
    val name: String,
    val email: String,
    val ageRange: String,
    val tutorName: String,
    val isLocked: Boolean,
    val statusText: String
)
