package com.example.promotr.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object OnboardingSelection : Screen("onboarding_selection")
    object OnboardingSteps : Screen("onboarding_steps")
    object Auth : Screen("auth")
    object Main : Screen("main")
}
