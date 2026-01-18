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
import com.example.promotr.ui.crew.profile.ProfileScreen
import com.example.promotr.ui.crew.earnings.EarningsScreen
import com.example.promotr.ui.crew.jobapplication.JobApplicationStep1Screen
import com.example.promotr.ui.crew.jobapplication.JobApplicationStep2Screen
import com.example.promotr.ui.crew.jobapplication.JobApplicationStep3Screen
import com.example.promotr.ui.crew.notifications.NotificationsScreen
import com.example.promotr.ui.models.JobListing
import com.example.promotr.ui.models.JobType

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
                    when (role) {
                        com.example.promotr.ui.auth.UserRole.CREW -> {
                            navController.navigate(Screen.BasicInfo.route) {
                                popUpTo(Screen.RoleSelection.route) { inclusive = true }
                            }
                        }
                        com.example.promotr.ui.auth.UserRole.ORGANIZER -> {
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.RoleSelection.route) { inclusive = true }
                            }
                        }
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
        
        composable(Screen.CrewHome.route) {
            com.example.promotr.ui.crew.home.HomeScreen(
                onNavigateToEarnings = {
                    navController.navigate(Screen.Earnings.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToJobApplication = { job ->
                    // Navigate to step 1 with job data (using default for now)
                    navController.navigate(Screen.JobApplicationStep1.route)
                }
            )
        }
        
        composable(Screen.BasicInfo.route) {
            com.example.promotr.ui.crew.registration.BasicInfoScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNext = {
                    navController.navigate(Screen.Experience.route)
                }
            )
        }
        
        composable(Screen.Experience.route) {
            com.example.promotr.ui.crew.registration.ExperienceScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNext = {
                    navController.navigate(Screen.PhotoUpload.route)
                }
            )
        }
        
        composable(Screen.PhotoUpload.route) {
            com.example.promotr.ui.crew.registration.PhotoUploadScreen(
                onBack = {
                    navController.popBackStack()
                },
                onCompleteKyc = {
                    navController.navigate(Screen.AadhaarUpload.route)
                },
                onGoToHome = {
                    navController.navigate(Screen.CrewHome.route) {
                        popUpTo(Screen.PhotoUpload.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.AadhaarUpload.route) {
            com.example.promotr.ui.crew.kyc.AadhaarUploadScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNext = {
                    navController.navigate(Screen.CrewHome.route) {
                        popUpTo(Screen.AadhaarUpload.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSettingsClick = {
                    // Handle settings navigation
                },
                onNavigateToHome = {
                    navController.navigate(Screen.CrewHome.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                },
                onNavigateToJobs = {
                    // Handle jobs navigation
                },
                onNavigateToEarnings = {
                    navController.navigate(Screen.Earnings.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                },
                onNavigateToProfile = {
                    // Already on profile
                }
            )
        }
        
        composable(Screen.Earnings.route) {
            EarningsScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.CrewHome.route) {
                        popUpTo(Screen.Earnings.route) { inclusive = true }
                    }
                },
                onNavigateToEarnings = {
                    // Already on earnings
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Earnings.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.JobApplicationStep1.route) {
            // Using a default job for now - in production, pass job ID and fetch from ViewModel
            val defaultJob = JobListing(
                id = "1",
                title = "Event Promoter",
                type = JobType.ONE_OFF_EVENT,
                pay = "$25",
                payType = "hr",
                location = "Convention Center, Downtown",
                date = "Sat, Oct 24",
                time = "9:00 AM - 5:00 PM",
                description = "Looking for energetic brand ambassadors to distribute flyers and engage with attendees at the annual Tech Summit."
            )
            JobApplicationStep1Screen(
                job = defaultJob,
                onBack = {
                    navController.popBackStack()
                },
                onNext = {
                    navController.navigate(Screen.JobApplicationStep2.route)
                }
            )
        }
        
        composable(Screen.JobApplicationStep2.route) {
            JobApplicationStep2Screen(
                onBack = {
                    navController.popBackStack()
                },
                onNext = {
                    navController.navigate(Screen.JobApplicationStep3.route) {
                        popUpTo(Screen.JobApplicationStep1.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.JobApplicationStep3.route) {
            JobApplicationStep3Screen(
                onGoToHome = {
                    navController.navigate(Screen.CrewHome.route) {
                        popUpTo(Screen.JobApplicationStep3.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
