package com.example.promotr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.promotr.ui.auth.LoginScreen
import com.example.promotr.ui.auth.OtpVerificationScreen
import com.example.promotr.ui.auth.RoleSelectionScreen
import com.example.promotr.ui.main.MainScreen
import com.example.promotr.ui.onboarding.OnboardingSelectionScreen
import com.example.promotr.ui.onboarding.OnboardingStepsScreen
import com.example.promotr.ui.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.OnboardingSelection.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToOnboardingSteps = {
                    navController.navigate(Screen.OnboardingSteps.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.OnboardingSelection.route) {
            OnboardingSelectionScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.OnboardingSelection.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.OnboardingSteps.route) {
            OnboardingStepsScreen(
                onNavigateToAuth = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.OnboardingSteps.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Auth.route) {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.OtpVerification.route)
                },
                defaultToPhone = false
            )
        }
        
        composable(Screen.OtpVerification.route) {
            OtpVerificationScreen(
                onBack = {
                    navController.popBackStack()
                },
                onVerify = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                onContinue = { role ->
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.RoleSelection.route) { inclusive = true }
                    }
                },
                onLogin = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.RoleSelection.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
