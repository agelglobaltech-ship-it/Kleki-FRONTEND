package com.example.klexifrontend

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.klexifrontend.screens.*
import com.example.klexifrontend.state.AppScreen
import com.example.klexifrontend.state.AppStateManager
import com.example.klexifrontend.theme.KlexiTheme

@Composable
@Preview
fun App() {
    val stateManager = remember { AppStateManager() }

    KlexiTheme {
        when (stateManager.currentScreen) {
            AppScreen.WELCOME -> WelcomeScreen(stateManager)
            AppScreen.ROLE_SELECTION -> RoleSelectionScreen(stateManager)
            AppScreen.LOGIN -> LoginScreen(stateManager)
            AppScreen.REGISTER_AGE -> RegisterAgeScreen(stateManager)
            AppScreen.REGISTER_FORM -> RegisterFormScreen(stateManager)
            AppScreen.STUDENT_HOME -> StudentDashboardScreen(stateManager)
            AppScreen.GAME_PLAY -> GamePlayScreen(stateManager)
            AppScreen.SENTENCE_GAME -> SentenceGameScreen(stateManager, onBack = { stateManager.currentScreen = AppScreen.WELCOME })
            AppScreen.KLEXI_SHOP -> KlexiShopScreen(stateManager)
            AppScreen.TUTOR_PANEL -> TutorDashboardScreen(stateManager)
            AppScreen.ADMIN_PANEL -> AdminDashboardScreen(stateManager)
            AppScreen.THERAPIST_PANEL -> TherapistDashboardScreen(stateManager)
        }
    }
}
