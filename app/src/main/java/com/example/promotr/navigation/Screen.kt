package com.example.promotr.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object OnboardingSelection : Screen("onboarding_selection")
    object OnboardingSteps : Screen("onboarding_steps")
    object Auth : Screen("auth")
    object OtpVerification : Screen("otp_verification")
    object RoleSelection : Screen("role_selection")
    object Main : Screen("main")
    object CrewHome : Screen("crew_home")
    object BasicInfo : Screen("basic_info")
    object Experience : Screen("experience")
    object PhotoUpload : Screen("photo_upload")
    object AadhaarUpload : Screen("aadhaar_upload")
    object Profile : Screen("profile")
    object Earnings : Screen("earnings")
    object JobApplicationStep1 : Screen("job_application_step1")
    object JobApplicationStep2 : Screen("job_application_step2")
    object JobApplicationStep3 : Screen("job_application_step3")
    object Notifications : Screen("notifications")
}
