package com.example.promotr.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object OnboardingSelection : Screen("onboarding_selection")
    object OnboardingSteps : Screen("onboarding_steps")
    object Auth : Screen("auth")
    object OtpVerification : Screen("otp_verification")
    object RoleSelection : Screen("role_selection")
    object Main : Screen("main")
}
