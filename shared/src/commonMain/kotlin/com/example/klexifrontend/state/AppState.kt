package com.example.klexifrontend.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.klexifrontend.models.AccessoryType
import com.example.klexifrontend.models.AdminUserSummary
import com.example.klexifrontend.models.AgeRange
import com.example.klexifrontend.models.KlexiShopItem
import com.example.klexifrontend.models.TherapistNote
import com.example.klexifrontend.models.UserProfile
import com.example.klexifrontend.models.UserRole

enum class AppScreen {
    WELCOME,
    ROLE_SELECTION,
    LOGIN,
    REGISTER_AGE,
    REGISTER_FORM,
    STUDENT_HOME,
    GAME_PLAY,
    SENTENCE_GAME,
    KLEXI_SHOP,
    TUTOR_PANEL,
    ADMIN_PANEL,
    THERAPIST_PANEL
}

class AppStateManager {
    var currentScreen by mutableStateOf(AppScreen.WELCOME)
    var selectedRole by mutableStateOf<UserRole?>(null)
    var currentUser by mutableStateOf(
        UserProfile(
            id = "user_1",
            fullName = "Sofía López",
            email = "sofia@klexi.app",
            role = UserRole.ALUMNO,
            ageRange = AgeRange.NINEZ_INTERMEDIA,
            tutorFullName = "María López",
            tutorEmail = "maria@klexi.app",
            points = 120,
            level = 1
        )
    )

    // Registration Form State
    var regAgeRange by mutableStateOf<AgeRange?>(null)
    var regFullName by mutableStateOf("")
    var regEmail by mutableStateOf("")
    var regPassword by mutableStateOf("")
    var regTutorFullName by mutableStateOf("")
    var regTutorEmail by mutableStateOf("")
    var regError by mutableStateOf<String?>(null)

    // Klexi Customization State
    var equippedAccessory by mutableStateOf<AccessoryType?>(AccessoryType.HAT)
    var klexiBodyColor by mutableStateOf(Color(0xFF20C997))
    var shopItems by mutableStateOf(
        listOf(
            KlexiShopItem("1", "Sombrero Mágico", AccessoryType.HAT, 100, isUnlocked = true, isEquipped = true),
            KlexiShopItem("2", "Gafas de Sol", AccessoryType.GLASSES, 80, isUnlocked = false, isEquipped = false),
            KlexiShopItem("3", "Moño Rosado", AccessoryType.BOW, 50, isUnlocked = false, isEquipped = false),
            KlexiShopItem("4", "Corona Real", AccessoryType.CROWN, 150, isUnlocked = false, isEquipped = false)
        )
    )
    var showRewardPopup by mutableStateOf(false)
    var justUnlockedItem by mutableStateOf<KlexiShopItem?>(null)

    // Admin State (Mock Registered Users)
    var adminUsers by mutableStateOf(
        listOf(
            AdminUserSummary("1", "Sofía López", "sofia@klexi.app", "6-8 años", "María López", false, "Activo"),
            AdminUserSummary("2", "Mateo Ramírez", "mateo@klexi.app", "9-12 años", "Juan Ramírez", false, "Activo"),
            AdminUserSummary("3", "Carlos Gómez", "carlos@klexi.app", "13-17 años", "Ana Gómez", true, "Bloqueado"),
            AdminUserSummary("4", "Lucía Fernández", "lucia@klexi.app", "3-5 años", "David Fernández", false, "Inactivo")
        )
    )

    // Therapist Notes State
    var therapistNotes by mutableStateOf(
        listOf(
            TherapistNote("1", "Sofía López", "15 Feb 2025", "Excelente progreso en fluidez de lectura. Se recomienda continuar con ejercicios de sílabas complejas.", "Lectura"),
            TherapistNote("2", "Sofía López", "10 Feb 2025", "Puede mejorar en comprensión de textos y asociación de conceptos.", "Comprensión")
        )
    )

    // Validation logic for registration password
    fun validatePassword(password: String): Boolean {
        // Minimum 8 characters, 1 uppercase, 1 digit, 1 special character from *#$%@
        val lengthOk = password.length >= 8
        val upperOk = password.any { it.isUpperCase() }
        val digitOk = password.any { it.isDigit() }
        val specialChars = setOf('*', '#', '$', '%', '@')
        val specialOk = password.any { it in specialChars }
        return lengthOk && upperOk && digitOk && specialOk
    }

    fun attemptRegister(): Boolean {
        if (regFullName.isBlank() || regEmail.isBlank()) {
            regError = "Por favor ingresa todos los campos obligatorios."
            return false
        }
        if (regAgeRange == null) {
            regError = "Por favor selecciona un rango de edad."
            return false
        }
        if (!validatePassword(regPassword)) {
            regError = "La contraseña debe tener mínimo 8 caracteres, 1 mayúscula, 1 dígito y 1 carácter especial (*#$%@)."
            return false
        }
        if (regAgeRange!!.requiresTutor && regTutorFullName.isBlank()) {
            regError = "Para menores de 18 años es obligatorio registrar al tutor."
            return false
        }

        // Register success
        val role = selectedRole ?: UserRole.ALUMNO
        currentUser = UserProfile(
            id = "user_${(100..999).random()}",
            fullName = regFullName,
            email = regEmail,
            role = role,
            ageRange = regAgeRange,
            tutorFullName = if (regAgeRange!!.requiresTutor) regTutorFullName else null,
            tutorEmail = if (regAgeRange!!.requiresTutor) (regTutorEmail.ifBlank { "tutor_$regEmail" }) else null,
            points = 120,
            level = 1
        )
        regError = null
        currentScreen = when (role) {
            UserRole.ALUMNO -> AppScreen.STUDENT_HOME
            UserRole.TUTOR -> AppScreen.TUTOR_PANEL
            UserRole.ADMIN -> AppScreen.ADMIN_PANEL
            UserRole.TERAPEUTA -> AppScreen.THERAPIST_PANEL
        }
        return true
    }

    fun buyOrEquipItem(item: KlexiShopItem) {
        if (item.isUnlocked) {
            // Equip/Unequip
            equippedAccessory = if (item.isEquipped) null else item.type
            shopItems = shopItems.map {
                if (it.id == item.id) it.copy(isEquipped = !item.isEquipped)
                else if (it.type == item.type) it.copy(isEquipped = false)
                else it
            }
        } else {
            // Buy item if points are sufficient
            if (currentUser.points >= item.price) {
                currentUser = currentUser.copy(points = currentUser.points - item.price)
                equippedAccessory = item.type
                shopItems = shopItems.map {
                    if (it.id == item.id) it.copy(isUnlocked = true, isEquipped = true)
                    else if (it.type == item.type) it.copy(isEquipped = false)
                    else it
                }
                justUnlockedItem = item
                showRewardPopup = true
            }
        }
    }

    fun toggleUserLock(userId: String) {
        adminUsers = adminUsers.map {
            if (it.id == userId) {
                val newLock = !it.isLocked
                it.copy(
                    isLocked = newLock,
                    statusText = if (newLock) "Bloqueado" else "Activo"
                )
            } else it
        }
    }

    fun addTherapistNote(text: String, area: String) {
        if (text.isNotBlank()) {
            val newNote = TherapistNote(
                id = "${therapistNotes.size + 1}",
                studentName = currentUser.fullName,
                date = "Hoy",
                text = text,
                area = area
            )
            therapistNotes = listOf(newNote) + therapistNotes
        }
    }
}
